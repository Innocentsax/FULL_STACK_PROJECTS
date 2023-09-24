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
public class MelaninHairCareScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public MelaninHairCareScrapper(ProductService productService) {
        this.productService = productService;
    }

    String webUrl = "https://melaninhaircare.com/collections/frontpage";
    String baseURL = "https://melaninhaircare.com";
    List<Element> productLinks = new CopyOnWriteArrayList<Element>();
    Pattern pattern  = Pattern.compile(".*(shampoo|cream|oil|conditioner).*", Pattern.CASE_INSENSITIVE);
    Pattern pattern2 = Pattern.compile("\\d+ ?oz", Pattern.CASE_INSENSITIVE);


    public void scrape(){
        log.info("Scrapping MelaninHairCare");
        getProductLinks();
    }
    public void getProductLinks() {
        try {

            Document doc = connect(webUrl);
            Elements elements = doc.select("div.product");
            for (Element element : elements) {
                if(element.select("div.ci > div.so.icn").isEmpty() && filterNonHairProduct(element)){
                    productLinks.add(element.select("div.ci > a").first());
                }
            }
            for (Element link : productLinks) {
                getProduct(link);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean filterNonHairProduct(Element element){
        String productName = element.select(".product-details a > h3").text();
        Matcher matcher = pattern.matcher(productName);
        return matcher.matches();
    }


    public void getProduct(Element link) throws Exception {
        String productLink = null;
        String ingredients = null;
        String price = null;
        String description;
        String image;
        String productName = null;
        String size = null;
        String brand = "Melanin Haircare";
        int cents = 100;
        boolean isAvailable = true;

        try{
            // convert page to generated HTML and convert to document
            productLink = baseURL + link.attr("href");
            Document doc = connect(productLink);
            productName = doc.getElementById("product-description").child(0).text();
            price = doc.getElementById("product-price").child(0).text();
            description = doc.select("div.rte").first().child(2).text();
            image = ("https:" + doc.getElementById("product-main-image").select("img")
                    .attr("src"))
                    .split("\\?")[0];
            ingredients = getIngredient(doc).replace("INGREDIENTS: ", "");
            size = getSize(doc.select(".rte .qualityIngredients h3").text());

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

    public String getIngredient(Document doc) throws Exception {
        return doc.select("table > tbody > tr > td").text();
    }

    private Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public String getSize(String str){
        Matcher matcher = pattern2.matcher(str);
        if (matcher.find()){
            return matcher.group();
        }
        return null;
    }

}
