package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.models.Category;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductServicesImplTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServicesImpl productServicesImpl;
    @Mock
    private UserRepository userRepository;

    User user;

    @Test
    void getAllProducts() {
        Category burger = Category.builder().categoryId(1L).categoryName("Burger").build();
        Product product = Product.builder().productId(1L).productName("Product 1").category(burger).build();
        Pageable pageable = PageRequest.of(0, 2, Sort.by("productId"));
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(product);
        Page<Product> products = new PageImpl<>(listOfProducts);
        Mockito.when(productRepository.findAll(pageable)).thenReturn(products);
        String sortBy = "productId";
        String sortDir = "asc";
        String filterBy = "";
        String filterParam = "";
        String productPriceStartRange= "";
        String productPriceEndRange= "";
        productServicesImpl.getAllProducts(0, 2, sortBy, sortDir, filterBy, filterParam,productPriceStartRange,productPriceEndRange);
        verify(productRepository).findAll(pageable);
    }

    @Test
    void test_fetchSingleProduct() {
        Category category = Category.builder()
                .categoryId(1L)
                .categoryName("sides")
                .build();
        Product product = Product.builder()
                .productId(1L)
                .productName("Cheesy Burger")
                .productPrice(1000.00)
                .category(category)
                .build();
        ProductDto productDto = ProductDto.builder().productId(1L).productName("Cheesy Burger").productPrice(1000.00)
                .categoryName("sides").build();
        user = User.builder().email("stanley@gmail.com").build();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getName()).thenReturn(user.getEmail());
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        productRepository.save(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productServicesImpl.fetchSingleProductById(1L);
        verify(productRepository).findById(any());

    }
}
