package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Category;
import com.decagon.chompapp.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryIntegrationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void injectedComponentsAreNotNull () {
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(jdbcTemplate);
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(productRepository);
    }

    @Test
    void whenProductIsSavedThenFindsAllByProductNameContainsWillNotReturnNull() {
        categoryRepository.save(Category.builder().categoryName("Burgers").build());
        productRepository.save(Product.builder().productName("Mexican Avocado Burger").size("small").category(categoryRepository.findByCategoryName("Burgers").orElseThrow()).build());
        Pageable pageable = PageRequest.of(0, 2, Sort.by("productId"));
        Assertions.assertNotNull(productRepository.findAllByProductNameContains(pageable,"ican"));
    }

    @Test
    void whenProductIsSavedThenFindsAllBySizeContainsWillNotReturnNull() {
        categoryRepository.save(Category.builder().categoryName("Burgers").build());
        productRepository.save(Product.builder().productName("Mexican Avocado Burger").size("small").category(categoryRepository.findByCategoryName("Burgers").orElseThrow()).build());
        Pageable pageable = PageRequest.of(0, 2, Sort.by("productId"));
        Assertions.assertNotNull(productRepository.findAllBySizeContains(pageable,"ma"));
    }

    @Test
    void whenProductIsSavedThenFindsAllByProductPriceBetweenRangeWillNotBeNull() {
        categoryRepository.save(Category.builder().categoryName("Burgers").build());
        productRepository.save(Product.builder().productName("Mexican Avocado Burger").size("small").quantity(555L).productPrice(491.00).category(categoryRepository.findByCategoryName("Burgers").orElseThrow()).build());
        Pageable pageable = PageRequest.of(0, 2, Sort.by("productId"));
        Assertions.assertNotNull(productRepository.findAllByProductPriceBetween(pageable,491.00,490.00,492.00));
    }

    @Test
    void findAllByCategory_CategoryName() {
        categoryRepository.save(Category.builder().categoryName("Burgers").build());
        productRepository.save(Product.builder().productName("Mexican Avocado Burger").size("small").quantity(555L).productPrice(490.00).category(categoryRepository.findByCategoryName("Burgers").orElseThrow()).build());
        Pageable pageable = PageRequest.of(0, 2, Sort.by("productId"));
        Assertions.assertNotNull(productRepository.findAllByCategory_CategoryName(pageable,"Burgers"));
    }
}