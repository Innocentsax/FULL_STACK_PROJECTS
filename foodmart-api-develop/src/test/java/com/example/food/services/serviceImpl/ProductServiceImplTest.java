package com.example.food.services.serviceImpl;

import com.example.food.dto.ProductDto;
import com.example.food.model.Category;
import com.example.food.model.Product;
import com.example.food.pojos.CreateProductResponse;
import com.example.food.pojos.ProductResponseDto;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.CategoryRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserUtil userUtil;

    @Test
    void testAddNewProduct() {
        Category category = new Category();
        category.setCategoryName("Category Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        category.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        category.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        category.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        category.setProducts(new ArrayList<>());
        when(categoryRepository.findByCategoryName((String) any())).thenReturn(category);

        Category category1 = new Category();
        category1.setCategoryName("Category Name");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        category1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        category1.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        category1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        category1.setProducts(new ArrayList<>());

        Product product = new Product();
        product.setCategory(category1);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        product.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setImageUrl("https://example.org/example");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        product.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        product.setProductName("Product Name");
        product.setProductPrice(BigDecimal.valueOf(42L));
        product.setQuantity(1);

        Category category2 = new Category();
        category2.setCategoryName("Category Name");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        category2.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        category2.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        category2.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        category2.setProducts(new ArrayList<>());

        Product product1 = new Product();
        product1.setCategory(category2);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        product1.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        product1.setDescription("The characteristics of someone or something");
        product1.setId(123L);
        product1.setImageUrl("https://example.org/example");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        product1.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        product1.setProductName("Product Name");
        product1.setProductPrice(BigDecimal.valueOf(42L));
        product1.setQuantity(1);
        Optional<Product> ofResult = Optional.of(product1);
        when(productRepository.save((Product) any())).thenReturn(product);
        when(productRepository.findByProductName((String) any())).thenReturn(ofResult);

        ProductDto productDto = new ProductDto();
        productDto.setCategoryName("Category Name");
        productDto.setDescription("The characteristics of someone or something");
        productDto.setImageUrl("https://example.org/example");
        productDto.setProductName("Product Name");
        productDto.setProductPrice(BigDecimal.valueOf(42L));
        productDto.setQuantity(1);
        CreateProductResponse actualAddNewProductResult = productServiceImpl.addNewProduct(productDto);
        assertEquals(-1, actualAddNewProductResult.getCode());
        assertEquals("Product Name", actualAddNewProductResult.getProductName());
        assertEquals("Product Name Already Exists!", actualAddNewProductResult.getDescription());
        verify(productRepository).findByProductName((String) any());
    }

}

