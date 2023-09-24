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
public class CurlSmithScrapper implements Scrapper {

    ProductService productService;

    @Autowired
    public CurlSmithScrapper(ProductService productService) {
        this.productService = productService;
    }

    List<String> curlSmithProductUrls = new CopyOnWriteArrayList<>();
    String baseUrl = "https://curlsmith.com/";

    public void getProductUrls() {
        try {
            Document landingPage = connect(baseUrl);
            Elements elements = landingPage.getElementsByClass("four columns alpha thumbnail even");


            for (Element element : elements) {
                String productsPageUrl = element.getElementsByTag("a").attr("href");
                curlSmithProductUrls.add(baseUrl + productsPageUrl);

            }

            Elements elements1 = landingPage.getElementsByClass("four columns  thumbnail odd");
            for (Element element1 : elements1) {
                String productsPageUrl = element1.getElementsByTag("a").attr("href");
                curlSmithProductUrls.add(baseUrl + productsPageUrl);
            }

            Elements elements2 = landingPage.getElementsByClass("four columns  thumbnail even");
            for (Element element2 : elements2) {
                String productsPageUrl = element2.getElementsByTag("a").attr("href");
                curlSmithProductUrls.add(baseUrl + productsPageUrl);
            }


            Elements elements3 = landingPage.getElementsByClass("four columns omega thumbnail odd");
            for (Element element3 : elements3) {
                String productsPageUrl = element3.getElementsByTag("a").attr("href");
                curlSmithProductUrls.add(baseUrl + productsPageUrl);

            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void getProducts() {
        getProductUrls();
        for (String productUrl : curlSmithProductUrls) {
            String ingredients;
            String price;
            String description;
            String image;
            String productName;
            String size = null;
            String brand = "Curlsmith";
            int cents = 100;
            boolean isAvailable = true;

            if (!productUrl.contains("-kit") && !productUrl.contains("-30-day") && !productUrl.contains("-3-step") && !productUrl.contains("3-month")) {

                try {
                    Document curlSmithProductPage = connect(productUrl);

                    productName = curlSmithProductPage.getElementsByClass("product_name").text();
                    price = curlSmithProductPage.getElementsByClass("current_price").text();
                    description = curlSmithProductPage.getElementById("section2").nextElementSibling().text();
                    String[] temp = curlSmithProductPage.select(".content .ingredient-image .imagetable").text()
                            .split(": ");
                    ingredients = temp[temp.length - 1];
                    image = "https:" + curlSmithProductPage.select(".relative.product_image img").attr("src");

                    if (productName.contains("(")) {
                        size = productName.substring(productName.indexOf("(") + 1, productName.indexOf(")"));
                    }
                    if (productCheck(ingredients, price, productName, description)) {
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
    }

    private Document connect (String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    @Override
    public void scrape () {
        log.info("Scrapping Curlsmith");
        getProducts();
    }
}
