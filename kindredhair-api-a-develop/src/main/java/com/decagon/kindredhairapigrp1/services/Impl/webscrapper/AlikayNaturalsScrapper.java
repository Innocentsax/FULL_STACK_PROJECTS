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
public class AlikayNaturalsScrapper implements Scrapper {
    ProductService productService;

    @Autowired
    public AlikayNaturalsScrapper(ProductService productService) {
        this.productService = productService;
    }

    String webUrl = "https://alikaynaturals.com/collections/hair?page=1&view=all";
    String baseURL = "https://alikaynaturals.com";
    List<Element> productLinks = new CopyOnWriteArrayList<>();

    public void scrape() {
        log.info("Scrapping AlikayNaturals");
        getProductLinks();
    }

    public void getProductLinks() {
        try {

            while (true) {
                Document doc = connect(webUrl);

                Elements elements = doc.select("div.product-top > div.product-image > a");
                productLinks.addAll(elements);
                Element linkToNextPage = doc.select("div.infinite-scrolling > a").first();
                if (linkToNextPage == null)
                    break;
                webUrl = baseURL + linkToNextPage.attr("href");
            }

            for (Element link : productLinks) {
                getProduct(link);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProduct(Element link) throws IOException {

        String productLink;
        String ingredients = null;
        String price;
        String description;
        String image;
        String productName;
        String size;
        String brand = "Alikay Naturals";
        final int cents = 100;
        boolean isAvailable = true;

        try{
            // convert page to generated HTML and convert to document
            productLink = baseURL + link.attr("href");
            Document doc = connect(productLink);
            productName = doc.select("header.product-title > h1").first().text();
            price = doc.select("div.total-price > span > span.money").text();

            if(doc.select("#tabs > ul > li:nth-child(2)") != null
                    && doc.select("#tabs > ul > li:nth-child(2) > a").text().toLowerCase().equals("ingredients") ){
                ingredients = doc.getElementById("tabs-2").select("p:first-child").text();
            }else if(doc.select("#tabs > ul > li:nth-child(3)") != null
                    && doc.select("#tabs > ul > li:nth-child(3) > a").text().toLowerCase().equals("ingredients") ){
                ingredients = doc.getElementById("tabs-3").text();
            }
            description = doc.getElementById("tabs-1").text();
            image = ("https:" + doc.getElementById("product-featured-image").attr("src")).split("\\?")[0];

            size = doc.select("div.swatch-element.available > label").text().split(" ")[0];
            if (size == null || size.isBlank()){ size = null;}


            if(productCheck(ingredients,price,productName, description)){
                Integer priceNum = (int) (Double.parseDouble(price.replaceAll("[$a-zA-Z ]", "")) * cents);
                Product product = new Product(productName, priceNum, size, description, ingredients, isAvailable, productLink, image, brand);
                generateOtherData(product);
                productService.save(product);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }


}
