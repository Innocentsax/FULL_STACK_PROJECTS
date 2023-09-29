package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;
    private Product product;
    private Product product1;
    private Product product2;
    private Product product3;
    private SubCategory subCategory;

    int offset=1;
    int size=2;
    String field="name";

    List<Product> productList=new ArrayList<>();
    private ProductCustResponseDto productCustResponseDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subCategory = new SubCategory();
        product = Product.builder().name("Tall dinning chair").price(2000.00).imageUrl("hgdhg")
                .color("green").description("strong black").subCategory(subCategory).build();

        product1 = Product.builder().name("Short dinning chair").price(6000.00).imageUrl("imageurl")
                .color("black").description("black").build();
        product2 = Product.builder().name("Long couch").price(5000.00).imageUrl("couchurl")
                .color("red").description("strong red beautiful couch").build();
        product3 = Product.builder().name("Bed frame").price(4000.00).imageUrl("hgdhg")
                .color("white").description("beautiful white bed frame").build();

        productList.add(product);
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);



        productCustResponseDto = ProductCustResponseDto.builder().name("Tall dinning chair").price(2000.00).imageUrl("hgdhg")
                .color("green").description("strong black").subCategory(subCategory).build();


    }

    @Test
    void fetchASingleProduct() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        Assertions.assertEquals(productCustResponseDto, productService.fetchASingleProduct(anyLong()));
    }

    @Test
    void fetchProductsWithPagination(){

        Page<Product> pagedProducts= new PageImpl(productList,Pageable.ofSize(size),4);
        when(productRepository.findAll(PageRequest.of(offset,size).withSort(Sort.by(field)))).thenReturn(pagedProducts);
        assertEquals(4, productService.productWithPaginationAndSorting(offset,size,field,false).getTotalElements());
    }
}