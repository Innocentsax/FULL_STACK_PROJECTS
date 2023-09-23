package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.ProductRequestDto;
import com.decagon.fitnessoapp.dto.ProductResponseDto;
import com.decagon.fitnessoapp.dto.UserProductDto;
import com.decagon.fitnessoapp.model.product.IntangibleProduct;
import com.decagon.fitnessoapp.model.product.TangibleProduct;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ProductResponseDto addProduct(ProductRequestDto requestDto) throws IOException;

    ProductResponseDto deleteProduct(Long productId);

    Page<TangibleProduct> getAllProduct(int pageSize, int pageNumber);

    Page<IntangibleProduct> getAllServices(int pageSize, int pageNumber);

    ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto);

    ProductResponseDto getProduct(Long productId);

    ProductResponseDto viewProductDetails(Long id, String productType);

    Page<UserProductDto> getAllProducts(int pageNumber);

    List<UserProductDto> searchAllProducts();

    List<?> searchProduct(String text);

    List<UserProductDto> getProductsNP();

    List<UserProductDto> getServicesNP();

    List<UserProductDto> getAllProductsNP();
}
