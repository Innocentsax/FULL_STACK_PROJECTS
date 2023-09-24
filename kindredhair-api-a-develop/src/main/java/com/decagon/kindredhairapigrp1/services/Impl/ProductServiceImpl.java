package com.decagon.kindredhairapigrp1.services.Impl;

import com.decagon.kindredhairapigrp1.DTO.ProductDTO;
import com.decagon.kindredhairapigrp1.models.Product;
import com.decagon.kindredhairapigrp1.repository.ProductRepository;
import com.decagon.kindredhairapigrp1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        Product currentProductInDatabase;
        if (product.getSize() != null) {
            currentProductInDatabase = productRepository.findProductByNameAndSize(product.getName(), product.getSize());
        } else{
            currentProductInDatabase = productRepository.findProductByName(product.getName());
        }
        if (currentProductInDatabase != null) {
            product.setId(currentProductInDatabase.getId());
            product.setShopifyID(currentProductInDatabase.getShopifyID());
        }
        //If product does not exist in the database yet or the price changes it should be uploaded/updated to shopify
        if(currentProductInDatabase == null || !currentProductInDatabase.getPrice().equals(product.getPrice())){
            product.setShopifyUpdateStatus(true);
        }

        product.setAvailable(true);
        productRepository.save(product);
    }

    @Override
    public List<ProductDTO> findAllAvailableProducts(){
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findAllByAvailable(true);
        products.forEach(product -> productDTOS
                .add( new ProductDTO.ProductDTOBuilder()
                .setProduct(product, false).build() ));
        return productDTOS;
    }

    /*
    Products whose last UpdatedAt timestamp falls before the most recent web-scrape should be set as unavailable
     */
    @Override
    public void setUnavailableProducts(Timestamp timeScrappingStarted) {
        List<Product> unavailableProducts = productRepository.findAllByUpdatedAtBefore(timeScrappingStarted);
        unavailableProducts.forEach(product -> {
            product.setAvailable(false);
            productRepository.save(product);
        });
    }

    @Override
    public List<Product> getAllByShopifyUpdateStatus(boolean status) {
        return productRepository.findAllByShopifyUpdateStatus(status);
    }
}
