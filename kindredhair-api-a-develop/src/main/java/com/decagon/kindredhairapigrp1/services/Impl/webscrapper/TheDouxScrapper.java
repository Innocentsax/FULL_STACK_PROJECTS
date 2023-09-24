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
public class TheDouxScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public TheDouxScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> productUrls = new CopyOnWriteArrayList<>();

    public void getProductUrls() {
        try {
            Document theDoux = connect("https://thedoux.com/products");

            Elements products = theDoux.select(".product");

            for (Element product : products) {
                productUrls.add("https://thedoux.com" + product.select("a").attr("href"));
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
            String description = null;
            String image;
            String productName;
            String size = null;
            String brand = "The Doux";
            int cents = 100;
            boolean isAvailable = true;

            try {
                Document productPage = connect(productUrl);

                Elements desc = productPage.select(".product-description strong");

                if (!desc.text().contains("Ingredients:")) {
                    continue;
                }

                productName = productPage.select(".page-title").text();

                image = productPage.select("#productSlideshow img").attr("data-src");

                price = productPage.select("#productDetails .sqs-money-native").first().text().replaceAll("[^0-9.]", "");

                for (Element element : desc) {
                    if (element.text().equals("Ingredients:")) {
                        ingredients = element.parent().text().substring(element.parent().text().indexOf("Ingredients: "))
                                .replaceAll("Ingredients: ", "");
                    }
                    if (element.text().contains("WHAT IT DOUX")) {
                        description = element.parent().text().replaceAll("WHAT IT DOUX[:?] ", "");
                        if (element.parent().text().equals(element.text()))
                            description = element.parent().nextElementSibling().text();
                    }
                }
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
        log.info("Scrapping The Doux");
        getProducts();
    }

}