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
public class JaneCarterScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public JaneCarterScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> productUrls = new CopyOnWriteArrayList<>();

    public void getProductUrls() {
        try {
            Document janeCarter = connect("https://janecartersolution.com/collections/all");

            Elements products = janeCarter.select(".product-item");

            for (Element product : products) {
                productUrls.add("https://janecartersolution.com"
                        + product.select("a").attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getProducts() {
        getProductUrls();
        for (String productUrl : productUrls) {
            String ingredients = null;
            String price = null;
            String description = null;
            String image;
            String productName;
            String size = null;
            String brand ="Jane Carter";
            int cents = 100;
            try {
                Document productPage = connect(productUrl);

                Element ingredientsSection = productPage.getElementById("section4");
                if (!(ingredientsSection != null && ingredientsSection.text().equals("List of Ingredients"))) {
                    continue;
                }
                ingredients = ingredientsSection.nextElementSibling().select("p").text();

                productName = productPage.select(".page-title").text();

                image = "https:" + productPage.select(".photo").attr("href");

                boolean isAvailable = true;
                String fullDescription = "";
                Element descriptionSection1 = productPage.getElementById("section1");
                Element descriptionSection2 = productPage.getElementById("section2");
                if (descriptionSection1 != null) {
                    fullDescription += descriptionSection1.nextElementSibling().text() + "\n";
                }
                if (descriptionSection2 != null) {
                    fullDescription += descriptionSection2.nextElementSibling().text();
                }
                if (fullDescription.length() > 0) {
                    description = fullDescription;
                }

                Element sizeSection = productPage.getElementById("section5");
                if (sizeSection != null && sizeSection.text().equals("Size")) {
                    size = sizeSection.nextElementSibling().select("p").text();
                    price = productPage.select(".actual-price").first().text().replaceAll("[^0-9.]", "");

                } else {
                    Elements variants = productPage.select(".variants option");
                    for (Element variant : variants) {
                        size = variant.text().substring(0, variant.text().indexOf(" -"));
                        price = variant.text().substring(variant.text().indexOf(" - ")).replaceAll("[^0-9.]", "");

                    }
                }


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
        log.info("Scrapping Jane Carter");
        getProducts();
    }

}
