package com.decagon.kindredhairapigrp1.services.Impl;

import com.decagon.kindredhairapigrp1.DTO.ShopifyProductDTO;
import com.decagon.kindredhairapigrp1.DTO.ShopifyRequestDTO;
import com.decagon.kindredhairapigrp1.models.Product;
import com.decagon.kindredhairapigrp1.repository.ProductRepository;
import com.decagon.kindredhairapigrp1.services.ProductService;
import com.decagon.kindredhairapigrp1.services.ShopifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static java.lang.Thread.sleep;

@Slf4j
@Service
public class ShopifyServiceImpl implements ShopifyService {
    @Value("#{environment['shopify.url']}")
            private String SHOPIFY_URL;

    @Value("#{environment['shopify.apiKey']}")
    private String SHOPIFY_APIKEY;

    @Value("#{environment['shopify.password']}")
            private String SHOPIFY_PASSWORD;

    ProductService productService;

    WebClient.Builder webClientBuilder;

    ProductRepository productRepository;

    public ShopifyServiceImpl(ProductService productService, WebClient.Builder webClientBuilder,  ProductRepository productRepository) {
        this.productService = productService;
        this.webClientBuilder = webClientBuilder;
        this.productRepository = productRepository;
    }

    @Override
    public void handleProductUploadsAndUpdateToShopify() {
        List<Product> productsToUploadAndUpdate = productService.getAllByShopifyUpdateStatus(true);
        upload(productsToUploadAndUpdate);
        if (!productsToUploadAndUpdate.isEmpty()){
            upload(productsToUploadAndUpdate);
        }
        //If after two attempts any products still fail to be posted then notify admin
        if (!productsToUploadAndUpdate.isEmpty()){
        //Tell am the thing no dey work
        }
    }
    /*
    This method handles shopify uploads. There's a sleep to accommodate shopify's requests per second limit.
    It checks the shopifyID to determine whether it's to be a POST or a PUT.
     */
    public void upload(List<Product> productsToUploadAndUpdate){
        productsToUploadAndUpdate.removeIf(product -> {
            try {
                sleep(600);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                return false;
            }
            return product.getShopifyID() == 0 ? uploadProductToShopify(product) : updateProductOnShopify(product);
        });
    }

    @Override
    public boolean uploadProductToShopify(Product productToUpload) {
        ShopifyProductDTO shopifyProductDTO = new ShopifyProductDTO.ShopifyProductDTOBuilder()
                .setProduct(productToUpload).build();
        ShopifyRequestDTO product = new ShopifyRequestDTO(shopifyProductDTO);

       try {
            String response = webClientBuilder.baseUrl(SHOPIFY_URL + ".json").build()
                   .post()
                    .headers(headers -> headers.setBasicAuth(SHOPIFY_APIKEY, SHOPIFY_PASSWORD))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(product)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            response = response.substring(0, response.indexOf(",")).replaceAll("\\{\"product\":\\{\"id\":", "");

            productToUpload.setShopifyID(Long.parseLong(response));
           productToUpload.setShopifyUpdateStatus(false);
            productRepository.save(productToUpload);
       } catch (Exception e) {
           log.error(e.getMessage());
           return false;
       }
        return true;
    }

    @Override
    public boolean updateProductOnShopify(Product productToUpdate) {
        ShopifyProductDTO shopifyProductDTO = new ShopifyProductDTO.ShopifyProductDTOBuilder()
                .setProduct(productToUpdate).build();
        ShopifyRequestDTO product = new ShopifyRequestDTO(shopifyProductDTO);

        try {
            String response = webClientBuilder.baseUrl(SHOPIFY_URL+ "/" + productToUpdate.getShopifyID() + ".json").build()
                    .put()
                    .headers(headers -> headers.setBasicAuth(SHOPIFY_APIKEY, SHOPIFY_PASSWORD))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(product)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            productToUpdate.setShopifyUpdateStatus(false);
            productRepository.save(productToUpdate);
            System.out.println(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}