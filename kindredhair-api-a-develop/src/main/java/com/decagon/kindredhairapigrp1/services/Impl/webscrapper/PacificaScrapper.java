package com.decagon.kindredhairapigrp1.services.Impl.webscrapper;

import com.decagon.kindredhairapigrp1.models.Product;
import com.decagon.kindredhairapigrp1.services.ProductService;
import com.decagon.kindredhairapigrp1.services.Scrapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;

@Slf4j
@Component
public class PacificaScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public PacificaScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> productUrls = new CopyOnWriteArrayList<>();

    public void getProductUrls() {
        try {
            Document pacifica = connect("https://www.pacificabeauty.com/collections/hair");
            Elements products = pacifica.select(".ProductItem__Title");

            for (Element product : products) {
                productUrls.add("https://www.pacificabeauty.com" + product.select("a").attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getProducts() {
        getProductUrls();
        for (String productUrl : productUrls) {
            String ingredients = null;
            String price;
            String description;
            String image;
            String productName;
            String size = null;
            String brand = "Pacifica";
            int cents = 100;
            boolean isAvailable = true;

            try {
                Document productPage = connect(productUrl);

                productName = productPage.select(".ProductMeta__Title").text();
                image = "https:" + productPage.select(".Product__Slideshow img").attr("data-original-src");

                price = productPage.select(".ProductMeta__Price").first().text().replaceAll("[^0-9.]", "");

                Elements siblingButtons = productPage.select(".Collapsible__Button");
                for (Element button : siblingButtons) {
                    if (button.text().equals("Ingredients")) {
                        ingredients = button.nextElementSibling().text();
                    }
                }

                description = productPage.select(".ProductMeta__Description .Rte > p").first().text();

                if(productCheck(ingredients,price,productName, description)){
                    Integer priceNum = (int) (Double.parseDouble(price.replaceAll("[$a-zA-Z ]", "")) * cents);
                    Product product = new Product(productName, priceNum, size, description, ingredients, isAvailable, productUrl, image, brand);
                    generateOtherData(product);
                    productService.save(product);
                }

                sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    @Override
    public void scrape() {
        log.info("Scrapping Pacifica");
        getProducts();
    }

}
