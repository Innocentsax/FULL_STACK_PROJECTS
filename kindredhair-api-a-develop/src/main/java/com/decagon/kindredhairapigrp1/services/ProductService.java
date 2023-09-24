package com.decagon.kindredhairapigrp1.services;

import com.decagon.kindredhairapigrp1.DTO.ProductDTO;
import com.decagon.kindredhairapigrp1.models.Product;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface ProductService {
    void save(Product product);
    List<ProductDTO> findAllAvailableProducts();
    List<Product> getAllByShopifyUpdateStatus(boolean status);
    void setUnavailableProducts(Timestamp timeScrappingStarted);
}
