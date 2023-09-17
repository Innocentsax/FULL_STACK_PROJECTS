package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.Category;
import com.decagon.safariwebstore.model.Product;
import com.decagon.safariwebstore.model.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAllByCategory(Category category, Pageable pageable);
    Page<Product> findAllByCategoryAndSubCategory(Category category, SubCategory subCategory,
                                                  Pageable pageable);
    Product findProductById(Long id);
    List<Product> findByNameContains(String name);
}
