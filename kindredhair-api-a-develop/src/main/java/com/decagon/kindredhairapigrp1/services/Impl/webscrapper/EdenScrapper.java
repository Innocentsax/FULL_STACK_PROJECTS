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
public class EdenScrapper implements Scrapper {
    ProductService productService;

    @Autowired
    public EdenScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> productUrls = new CopyOnWriteArrayList<>();

    String productLink = null;
    String ingredients = null;
    String price = null;
    String description;
    String image;
    String productName = null;
    String size = null;
    String brand ="Eden Bodyworks";
    int cents = 100;

    public void scrape() {
        log.info("Scrapping Eden Bodyworks");
        getProducts();
    }

    public void getProductUrls(String url) {
        try {
            Document eden = connect(url);
            Elements products = eden.getElementsByClass("product-link");

            for (Element product : products) {
                productUrls.add("https://edenbodyworks.com" + product.attr("href"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Method to get product properties for each product
     */
    public void getProducts() {
        getProductUrls("https://edenbodyworks.com/collections/all");
        getProductUrls("https://edenbodyworks.com/collections/all?page=2");

        for (String productUrl : productUrls) {
            try {
                Document productPage = connect(productUrl);

                productLink = productUrl;
                boolean isAvailable = true;
                productName = productPage.select(".detail .title").text();
                image = "https:" + productPage.select(".rimage-wrapper noscript img").first().attr("src");

                price = productPage.select(".price").first().text();//.replaceAll("[^0-9.]", "");

                String benefits = "";
                String recommendedFor = "";
                Elements buttons = productPage.select(".custom-field--title");
                for (Element button : buttons) {
                    switch (button.text()) {
                        case "Ingredients":
                            ingredients = button.nextElementSibling().text();
                            break;
                        case "Benefits":
                            benefits = button.nextElementSibling().text();
                            break;
                        case "Recommended For":
                            recommendedFor = button.nextElementSibling().text();
                            break;
                    }
                }

                if ((benefits + recommendedFor).length() > 0) {
                    description = benefits + recommendedFor;
                }

                if(productCheck(ingredients,price,productName, description)){
                    Integer priceNum = (int) (Double.parseDouble(price.replaceAll("[$a-zA-Z ]", "")) * cents);
                    Product product = new Product(productName, priceNum, size, description, ingredients, isAvailable, productUrl, image, brand);
                    generateOtherData(product);
                    productService.save(product);
                }
                sleep(1000);
            } catch (IOException  | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}
