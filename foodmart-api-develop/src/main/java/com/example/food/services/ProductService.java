package com.example.food.services;
import com.example.food.dto.UpdateProductDto;
import com.example.food.pojos.UpdatedProductResponse;
import com.example.food.dto.ProductDto;
import com.example.food.dto.ProductSearchDto;
import com.example.food.pojos.CreateProductResponse;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.ProductResponse;
import com.example.food.pojos.ProductResponseDto;

public interface ProductService {
    PaginatedProductResponse searchProduct(String filter,String sortBy, String sortDirection, int pageNumber, int pageSize);

    UpdatedProductResponse updateProduct(Long productId, UpdateProductDto productDto);

    CreateProductResponse addNewProduct(ProductDto productDto);

    ProductResponse fetchAllProducts();

    ProductResponseDto fetchSingleProduct(Long productId);

}
