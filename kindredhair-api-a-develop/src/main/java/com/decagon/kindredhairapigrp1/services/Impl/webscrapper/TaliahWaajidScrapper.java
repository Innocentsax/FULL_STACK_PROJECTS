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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class TaliahWaajidScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public TaliahWaajidScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> productLink = new CopyOnWriteArrayList<>();


    public void productUrls() {
        List<String> linkCollection = new ArrayList<>();

        try {

            String baseUrl = "https://naturalhair.org/collections";
            Document document = Jsoup.connect(baseUrl).get();
            Elements elements = document.getElementsByClass("grid__item col-md-4 col-sm-6 col-xxs-6");

            for (Element element : elements) {
                String links = element.getElementsByTag("a").attr("href");
                linkCollection.add( "https://naturalhair.org"+links);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String collectionList : linkCollection) {

            try {
                Document document = Jsoup.connect(collectionList).get();
                Elements elements = document.getElementsByClass("product-layout grid__item grid__item--collection-template col-md-4 col-sm-4 col-xxs-6 col-xs-12");

                for (Element element : elements) {
                    String productPage = element.getElementsByClass("grid-view-item__link image-ajax").attr("href");
                    productLink.add("https://naturalhair.org"+productPage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void productProperties() {
        productUrls();

        String ingredients = null;
        String price = null;
        String description = null;
        String image = null;
        String productName = null;
        String size = null;
        String brand = "Taliah Waajid";
        int cents = 100;
        boolean isAvailable = true;

        try {

            for (String links : productLink) {
                Document document = Jsoup.connect(links).get();
                Elements elements = document.getElementsByClass("product-single");

                for (Element element : elements) {
                    image = "https:" + element.getElementsByTag("img").attr("src");
                    productName = element.getElementsByClass("product-single__title").text();
                    price = element.getElementById("ProductPrice-product-template").text().trim();
                    ingredients = element.select("#tabs1 p ").last().text().replaceAll("Ingredients: ", "");

                    if(productName.contains("oz")) {
                        size = productName.substring(productName.length()-4);
                    }

                    Elements descriptions = element.select("p strong");
                    for (Element descriptionElement : descriptions) {
                        if (descriptionElement.text().contains("What It Does:")) {
                            description = descriptionElement.parent().text().replaceAll("What It Does: ", "");
                        }
                    }

                }

                if (productCheck(ingredients, price, productName, description)) {
                    Integer priceNum = (int) (Double.parseDouble(price.replaceAll("[$a-zA-Z ]", "")) * cents);
                    Product product = new Product(productName, priceNum, size, description, ingredients, isAvailable, links, image, brand);
                    generateOtherData(product);
                    productService.save(product);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void scrape() {
        log.info("Scrapping Taliah Waajid");
        productProperties();
    }
}
