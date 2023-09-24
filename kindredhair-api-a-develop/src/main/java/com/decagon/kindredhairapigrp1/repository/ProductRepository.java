package com.decagon.kindredhairapigrp1.repository;

import com.decagon.kindredhairapigrp1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductByName(String name);
    Product findProductByNameAndSize(String name, String size);
    List<Product> findAllByShopifyUpdateStatus(boolean shopifyUpdateStatus);
    List<Product> findAllByAvailable(boolean available);
    List<Product> findAllByUpdatedAtBefore(Timestamp timeScrappingStarted);
}
