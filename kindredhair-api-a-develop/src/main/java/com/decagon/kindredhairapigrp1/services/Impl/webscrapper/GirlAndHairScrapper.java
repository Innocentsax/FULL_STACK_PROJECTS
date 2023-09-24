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
public class GirlAndHairScrapper implements Scrapper {
    ProductService productService;

    @Autowired
    public GirlAndHairScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> productUrls = new CopyOnWriteArrayList<>();


    public void getProductUrls() {
        try {
            Document girlAndHair = Jsoup.connect("https://www.girlandhair.com/collections/under-hair-care").get();
            Elements products = girlAndHair.getElementsByClass("product-grid--title");

            for (Element product : products) {
                productUrls.add("https://www.girlandhair.com" + product.select("a[href]").attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getProducts() {
        getProductUrls();
        for (String productUrl : productUrls) {
            String productLink;
            String ingredients = null;
            String price;
            String description = null;
            String image;
            String productName;
            String size;
            String brand ="Girl + Hair";
            int cents = 100;
            boolean isAvailable = true;

            try {

                Document productPage = connect(productUrl);
                productLink = productUrl;

                productName = productPage.select(".product-details-product-title").text();
                image = "https:" + productPage.select(".product-single__photo").first().attr("src");

                price = productPage.select(".money").first().text().replaceAll("[^0-9.]", "");

                Elements sections = productPage.select("h3");
                for (Element section : sections){
                    if (section.text().equals("Ingredients")) {
                        ingredients = section.nextElementSibling().getElementsByTag("em").text();
                    }
                    if (section.text().equals("Description")) {
                        description = section.nextElementSibling().text();
                    }
                }

                size = productPage.select(".product-description li").last().text();

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
        log.info("Scrapping Girl + Hair");
        getProducts();
    }
}
