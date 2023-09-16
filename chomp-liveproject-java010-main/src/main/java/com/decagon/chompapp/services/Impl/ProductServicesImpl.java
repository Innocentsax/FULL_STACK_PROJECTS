package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.dtos.ProductResponse;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.services.ProductServices;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServicesImpl implements ProductServices {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ProductResponse> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir, String filterBy, String filterParam, String startRange, String endRange) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())  ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        if ("".equals(filterBy) && !"".equals(filterParam)) {
            Page<Product> products = productRepository.findAllByFilterParam(pageable,filterParam.toLowerCase());
            return getProductResponseEntity(products);
        } else {
            switch (filterBy) {
                case "productName": {
                    Page<Product> products = productRepository.findAllByProductNameContains(pageable, filterParam.toLowerCase());
                    return getProductResponseEntity(products);
                }
                case "size": {
                    Page<Product> products = productRepository.findAllBySizeContains(pageable, filterParam.toLowerCase());
                    return getProductResponseEntity(products);
                }
                case "productPrice": {
                    Page<Product> products = productRepository.findAllByProductPriceBetween(pageable, Double.parseDouble(filterParam), Double.parseDouble(startRange), Double.parseDouble(endRange));
                    return getProductResponseEntity(products);
                }
                case "categoryName": {
                    Page<Product> products = productRepository.findAllByCategory_CategoryName(pageable, filterParam.toLowerCase());
                    return getProductResponseEntity(products);
                }
                default:
                    Page<Product> products = productRepository.findAll(pageable);
                    return getProductResponseEntity(products);
            }
        }
    }

    @Override
    public ResponseEntity<ProductDto> fetchSingleProductById(Long productId) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.findByEmail(loggedInUserName).orElseThrow(() -> new RuntimeException("User not found."));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found."));
        ProductDto productDto = convertProductEntityToDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }


    private ResponseEntity<ProductResponse> getProductResponseEntity(Page<Product> products) {
        List<Product> listOfProducts = products.getContent();
        var content =  listOfProducts.stream().map(this::convertProductEntityToDto).collect(Collectors.toList());

        ProductResponse productResponse= ProductResponse.builder()
                .content(content)
                .pageNo(products.getNumber())
                .totalPages(products.getTotalPages())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .last(products.isLast())
                .build();

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(productResponse, httpHeaders, HttpStatus.OK);
    }


    public ProductDto convertProductEntityToDto (Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .size(product.getSize())
                .quantity(product.getQuantity())
                .productPrice(product.getProductPrice())
                .productImage(product.getProductImage())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }
}
