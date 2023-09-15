package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<Product> createProduct(ProductDto productDto);
    ResponseEntity<Product> addProductImage(String img, Long productId);
    ResponseEntity<String> deleteProduct(Long id);
    ProductDto updateProduct(ProductDto productDto, Long productId);
}