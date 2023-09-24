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
public class BriogeoHairScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public BriogeoHairScrapper(ProductService productService) {
        this.productService = productService;
    }

    String webUrl = "https://briogeohair.com/collections/all-products";
    String baseURL = "https://briogeohair.com";

    List<Element> productLinks = new CopyOnWriteArrayList<Element>();

    public void scrape() {
        log.info("Scrapping Briogeohair");
        getProductLinks();
    }

    public void getProductLinks() {
        try {
            Document doc = connect(webUrl);
            Elements elements = doc.select("a.newcol-product");
            productLinks.addAll(elements);
            for (Element link : productLinks) {
                getProduct(link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProduct(Element link) throws IOException {
        String productLink = null;
        String ingredients = null;
        String price = null;
        String description;
        String image;
        String productName = null;
        String size = null;
        String brand ="Briogeo";
        int cents = 100;
        boolean isAvailable = true;

        try{
            // convert page to generated HTML and convert to document
            productLink = baseURL + link.attr("href");
            Document doc = connect(productLink);
            productName = doc.select("h1.pdp-details-title").first().text();
            price = doc.select("h5.pdp-details-price > span").attr("flow-default");
            if(doc.select("div.product-info-content.product-info-rte").size() > 0){
                ingredients = doc.select(".modal-ingredients__list").text().split("derived")[0] + "derived.";
            }

            description = doc.select(".product-info-container .product-info-content-column:nth-child(2):nth-child(2) p:first-of-type").text();
                    image = ("https:" + link.select("img.newcol-product-img-first").attr("src")).split("\\?")[0];

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
