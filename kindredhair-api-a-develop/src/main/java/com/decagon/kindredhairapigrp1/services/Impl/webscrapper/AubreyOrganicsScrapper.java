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
public class AubreyOrganicsScrapper implements Scrapper {
    ProductService productService;

    @Autowired
    public AubreyOrganicsScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> productUrls = new CopyOnWriteArrayList<>();
    List<String> productBaseUrls = new CopyOnWriteArrayList<>();
    String baseUrl = "https://aubreyorganics.com";


    public void getProductUrls() {
        productBaseUrls.add("https://aubreyorganics.com/collections/shampoo");
        productBaseUrls.add("https://aubreyorganics.com/collections/conditioners");
        productBaseUrls.add("https://aubreyorganics.com/collections/styling");

        try {
            for(String url : productBaseUrls) {
                Document document = connect(url);
                Elements elements = document.getElementsByClass("collection__product__container");

                for (Element element :elements) {
                    productUrls.add(url + element.select(".collection__product__link").attr("href"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getProducts(){
        getProductUrls();
        for (String productUrl : productUrls) {
            String ingredients;
            String price;
            String description;
            String image;
            String productName;
            String size;
            String brand = "Aubrey Organics";
            int cents = 100;
            boolean isAvailable = true;

            try {
                Document productPage = connect(productUrl);

                productName = productPage.select(".pdp-title").text();
                image = "https:" + productPage.select(" div.swiper-slide >img").attr("src");
                price = productPage.select("#nutra__fullPrice").text();
                size = productPage.select(".pdp-subtitle").text();
                ingredients = productPage.select(".section_content").text();
                description = productPage.select(".product__description").select(">div >p").text();


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
        log.info("Scrapping Aubrey Organics");
        getProducts();
    }

}
