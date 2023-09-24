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

@Slf4j
@Component
public class MauiMoistureScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public MauiMoistureScrapper(ProductService productService) {
        this.productService = productService;
    }


    private final List<String> productUrls = new CopyOnWriteArrayList<>();

    public void scrape() {
        log.info("Scrapping Maui Moisture");
        productUrls.add("https://www.mauimoisture.com/shop/type/shampoo/");
        productUrls.add("https://www.mauimoisture.com/shop/type/conditioner/");
        productUrls.add("https://www.mauimoisture.com/shop/type/treatments/");
        productUrls.add("https://www.mauimoisture.com/shop/type/styling/");

        for (String url : productUrls) {
            try {
                Document productUrlDocument = connect(url);
                Elements productElements = productUrlDocument.select(".woocommerce.columns-4 .products .product.type-product a.woocommerce-LoopProduct-link");

                for (Element element: productElements) {
                    String productLink = element.attr("href");
                    Document productDetailsDocument = connect(productLink);

                    String productName = productDetailsDocument.select(".container-fluid .col-md-pull-4 .product-item__category").text()
                            + " " + productDetailsDocument.select(".container-fluid .col-md-pull-4 .product-item__title").text()
                            .replaceAll("\\([\\w ].*\\)", "");
                    String description = productDetailsDocument.select("#collapseOne > div p:first-child").text();
                    String ingredients = productDetailsDocument.select("#collapseThree > p:first-child").text();

                    int cents = 100;
                    Integer price = Integer.parseInt("000") * cents;
                    String size = getProductSize(element.select("span img").attr("title"));
                    String image =productDetailsDocument.select(".container-fluid .col-md-4 .thumbnails > a").attr("href");
                    String brand =  "Maui Moisture";
                    boolean isAvailable = true;

                    if(productCheck(ingredients,price.toString(),productName, description)){
                        Product product = new Product(productName, price, size, description, ingredients, isAvailable, productLink, image, brand);
                        generateOtherData(product);
                        productService.save(product);

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getProductSize(String name){
        String[] arr = name.split("-");
         return  arr[arr.length - 1];
    }


    private Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
