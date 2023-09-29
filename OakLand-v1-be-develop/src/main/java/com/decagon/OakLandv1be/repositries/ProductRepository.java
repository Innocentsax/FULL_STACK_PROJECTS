package com.decagon.OakLandv1be.repositries;


import com.decagon.OakLandv1be.entities.Item;

import com.decagon.OakLandv1be.entities.Customer;

import com.decagon.OakLandv1be.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.List;
import java.util.stream.DoubleStream;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    @Query(value = "SELECT * FROM product_tbl ORDER BY created_at DESC LIMIT 3", nativeQuery = true)
    List<Product> findProductByCreatedAtDesc();
    @Query(value = "SELECT * FROM product_tbl ORDER BY sales DESC LIMIT 3", nativeQuery = true)
           List <Product> findProductsBySalesDesc();

    Product findByItem(Item item);
    Product findByItemProductName(String itemName);
    @Query(value="SELECT * FROM product_tbl WHERE sub_category_id=?", nativeQuery = true)
    List<Product> findAllBySubCategoryId(Long subCategoryId);
}
