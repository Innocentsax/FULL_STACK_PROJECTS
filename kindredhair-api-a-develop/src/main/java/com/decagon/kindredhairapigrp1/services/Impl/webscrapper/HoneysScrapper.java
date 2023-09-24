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
public class HoneysScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public HoneysScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> honeyProductUrls = new CopyOnWriteArrayList<>();
    String baseUrl = "https://www.honeyshandmade.com/collections/hair-care?page=";


    public void productPageUrl() {
        for(int i = 1; i <= 8; i++) {
            String pageUrl = baseUrl+i;
            honeyProductUrls.add(pageUrl);
        }
    }

    public void productUrl() {
        productPageUrl();

        String ingredients;
        String price;
        String description = null;
        String image;
        String productName;
        String size = null;
        String brand = "Honey’s Handmade";
        int cents = 100;
        boolean isAvailable = true;

        for (String url : honeyProductUrls) {
            try {
                Document productsPage = Jsoup.connect(url).get();
                Elements elements = productsPage.getElementsByClass("aspect-product__wrapper");

                for (Element element : elements) {
                    String productLink = "https://www.honeyshandmade.com/" + element.attr("href");
                    Document productPage = Jsoup.connect(productLink).get();
                    image = "https:" + productPage.getElementsByClass("zoom_enabled zoom FeaturedImage-product-template").attr("href");
                    productName = productPage.getElementsByClass("product_title entry-title").text();
                    price = productPage.getElementById("ProductPrice-product-template").text();

                    ingredients = productPage.select("#tab-description").text().replaceAll("Ingredients:", "");


                    if (productPage.select(".large-6 .product_infos .product-inner-data > div:eq(3)") != null) {
                        description = productPage.select(".large-6 .product_infos .product-inner-data > div:eq(3)").text();
                    }

                    if (productCheck(ingredients, price, productName, description)) {
                        Integer priceNum = (int) (Double.parseDouble(price.replaceAll("[$a-zA-Z ]", "")) * cents);
                        Product product = new Product(productName, priceNum, size, description, ingredients, isAvailable, productLink, image, brand);
                        generateOtherData(product);
                        productService.save(product);
                    }

                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    @Override
    public void scrape() {
        log.info("Scrapping Honey’s Handmade");
        productUrl();
    }
}
