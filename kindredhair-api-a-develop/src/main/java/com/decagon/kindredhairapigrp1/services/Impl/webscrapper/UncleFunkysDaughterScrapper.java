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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class UncleFunkysDaughterScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public UncleFunkysDaughterScrapper(ProductService productService) {
        this.productService = productService;
    }

    final String webUrl = "https://unclefunkysdaughter.com/hair-care.html";
    List<Element> productLinks = new CopyOnWriteArrayList<Element>();
    Pattern pattern = Pattern.compile("\\d+ ?oz", Pattern.CASE_INSENSITIVE);

    public void scrape() {
        log.info("Scrapping UncleFunkysDaughter");
        getProductLinks();
    }

    public void getProductLinks() {
        try {
            Document doc = connect(webUrl);
            Elements links = doc.select(".product.photo.product-item-photo > a:first-child");
            for (Element link : links) {
                getProduct(link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSize(String str){
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            return matcher.group();
        }
        return null;
    }

    public void getProduct(Element link) throws IOException {

        try{
            // convert page to generated HTML and convert to document
            int cents = 100;
            boolean isAvailable = true;
            String brand = "Uncle Funky's Daughter";
            String size = null;
            String productLink = link.attr("href");
            Document doc = connect(productLink);
            String image = link.child(0).attr("data-src");
            String productName = doc.select("h1.page-title > span").text()
                    + " " + doc.select(".product.attribute.overview .value").text();
            String price = doc.select("div.product-info-price div.price-box.price-final_price " +
                    "> span.price-container.price-final_price.tax.weee > span.price-wrapper > span.price").text();
            String ingredients = doc.select("div.ingredient > div > table tbody > tr > td").text();
            String description = doc.select("div.romance-language .product-description p:first-child").text();
            if (description.isBlank()) {
                description = doc.select("div.romance-language .product-description").text();
            }
            size = getSize(productName);
            if(size == null){
                size = getSize(description);
            }
            if(size == null){
                size = getSize(doc.select("div.romance-language .product-description p:nth-of-type(2)").text());
                description = doc.select("div.romance-language .product-description p:nth-of-type(2)").text();
            }
            if(size == null){
                size = getSize(doc.select("div.romance-language .product-description").text());
                description = doc.select("div.romance-language .product-description").text();
            }

            if(productCheck(ingredients,price,productName, description)) {
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
