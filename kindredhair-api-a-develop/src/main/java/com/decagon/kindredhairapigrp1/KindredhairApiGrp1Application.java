package com.decagon.kindredhairapigrp1;

import com.decagon.kindredhairapigrp1.services.ProductService;
import com.decagon.kindredhairapigrp1.services.ShopifyService;

import com.decagon.kindredhairapigrp1.utils.WebScrapping.WebScrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;

@SpringBootApplication
@EnableSwagger2
public class KindredhairApiGrp1Application {

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KindredhairApiGrp1Application.class, args);
        WebScrapper app = run.getBean(WebScrapper.class);
        Timestamp timeScrappingStarted = new Timestamp(System.currentTimeMillis());
        app.runApp();

        ProductService productService = run.getBean(ProductService.class);
        productService.setUnavailableProducts(timeScrappingStarted);

        ShopifyService shopifyService = run.getBean(ShopifyService.class);
        shopifyService.handleProductUploadsAndUpdateToShopify();
    }

}