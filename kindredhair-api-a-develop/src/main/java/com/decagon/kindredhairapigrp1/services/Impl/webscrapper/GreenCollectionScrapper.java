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
public class GreenCollectionScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public GreenCollectionScrapper(ProductService productService) {
        this.productService = productService;
    }

    final String webUrl = "https://curls.biz/";
    List<Element> productLinks = new CopyOnWriteArrayList<Element>();
    List<String> collections = new CopyOnWriteArrayList<String>();
    Pattern pattern = Pattern.compile("\\d+ ?oz", Pattern.CASE_INSENSITIVE);

    public void scrape() {
        log.info("Scrapping GreenCollection");
        getProductLinks();
    }

    public void getProductLinks() {
        try {
            Document doc = connect(webUrl);

            Elements links = doc.select("#menu-collections > li.menu-item.menu-item-type-post_type > a");
            for (Element link : links) {
                collections.add(link.attr("href"));
            }

            for (String link : collections) {
                doc = connect(link);
                links = doc.select("h3.product-title > a");
                for (Element element : links) {
                    productLinks.add(element);
                    getProduct(element);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProduct(Element link) throws IOException {
        try{
            // convert page to generated HTML and convert to document
            final int cents = 100;
            String brand = "Green Collection";
            String ingredients = null;
            boolean isAvailable = true;

            Document doc = connect(link.attr("href"));
            String productLink = link.attr("href");
            if (productLink.equals("https://curls.biz/product/so-so-clean-vitamin-c-curl-wash/")
            || productLink.equals("https://curls.biz/product/rice-water-and-coconut-fruit-refresher/")) {
                ingredients = doc.select(".woocommerce-Tabs-panel--ingredient_tab.panel ul").text();
            } else {
                ingredients = doc.select(".woocommerce-Tabs-panel--ingredient_tab.panel p").text();
            }

            String image = doc.select("figure.woocommerce-product-gallery__wrapper > div > a > img").attr("src");
            String productName = doc.select("h1.product_title.entry-title").first().text();

            String price = doc.select("span.woocommerce-Price-amount.amount > bdi").first().text();

            String description = doc.select("div.post-content.woocommerce-product-details__short-description").text()
                    + doc.select("div.woocommerce-Tabs-panel--additional_information .woocommerce-product-attributes-item__value").text();

            String size = doc.select("div.post-content.woocommerce-product-details__short-description > p > strong").text();

            if(size == null){
                size = getSize(productName);
            }

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

    public String getSize(String str){
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            return matcher.group();
        }
        return null;
    }

}
