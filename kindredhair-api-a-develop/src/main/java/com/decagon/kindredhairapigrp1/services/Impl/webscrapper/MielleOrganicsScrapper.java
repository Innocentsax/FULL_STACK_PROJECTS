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
public class MielleOrganicsScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public MielleOrganicsScrapper(ProductService productService) {
        this.productService = productService;
    }

    String webUrl = "https://mielleorganics.com/collections/all";
    String baseURL = "https://mielleorganics.com";
    List<Element> productLinks = new CopyOnWriteArrayList<Element>();

    public void scrape() {
        log.info("Scrapping MielliOrganics");
        getProductLinks();
    }

    public void getProductLinks() {
        try {

            while (true) {
                Document doc = connect(webUrl);

                Elements elements = doc.select("div.four.columns > div.product-wrap > div.relative.product_image > a");
                productLinks.addAll(elements);
                Element linkToNextPage = doc.select("div.js-load-more.load-more > a").first();
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

    public void getProduct(Element link) {
        String productLink = null;
        String ingredients = null;
        String price = null;
        String description = null;
        String image = null;
        String productName = null;
        String size = null;
        final int cents = 100;
        String brand = "Mielle Organics";
       boolean isAvailable = true;

        try{
            // convert page to generated HTML and convert to document
            productLink = baseURL + link.attr("href");
            Document doc = connect(productLink);

            productName = doc.select("h2.product_name").text();
            price = doc.select("span.current_price > span > span > span.money").text();
            description = getDescription(doc);
            image = ("https:" + doc.select("div.image__container > img").attr("src")).split("\\?")[0];
            ingredients = getIngredient(doc);

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

    public String getDescription(Document doc){
        String  description = "";
        if(doc.select("div.description.bottom > h3").first() != null){
            description = doc.select("div.description.bottom > h3").first().text();
        }

        if( description.isBlank() && doc.select("div.description.bottom > h3").first() != null){
            description = doc.select("div.description.bottom h3").first().nextElementSibling().text();
        }

        if(description.isBlank()){
            Elements links = doc.select("div.description.bottom > ul.tabs > li > a");
            for(Element a: links ){
                if("product".equals(a.text().toLowerCase()) || "product".equals(a.text().toLowerCase())){
                    String id = a.attr("href").replaceAll("#", "");
                    description = doc.getElementById(id).text();
                }
            }
        }
        return description;
    }


    public String getIngredient(Document doc) throws IOException {

        Elements elements = doc.select("div.description.bottom > h3");
        for(Element h3: elements ){
            if("ingredients".equals(h3.text().toLowerCase()) || "ingredient".equals(h3.text().toLowerCase())){
                return h3.nextElementSibling().text();
            }
        }
        Elements links = doc.select("div.description.bottom > ul.tabs > li > a");
        for(Element a: links ){
            if("ingredients".equals(a.text().toLowerCase()) || "ingredient".equals(a.text().toLowerCase())){
                String id = a.attr("href").replaceAll("#", "");
                return doc.getElementById(id).text();
            }
        }
        return "";

    }
    private Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}
