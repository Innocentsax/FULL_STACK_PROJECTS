package com.example.food.service.serviceImpl;
import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.Enum.Role;
import com.example.food.dto.ProductDto;
import com.example.food.dto.ProductSearchDto;
import com.example.food.dto.UpdateProductDto;
import com.example.food.model.Category;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.CreateProductResponse;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.UpdatedProductResponse;
import com.example.food.repositories.UserRepository;
import com.example.food.services.serviceImpl.ProductServiceImpl;
import com.example.food.util.UserUtil;
import com.example.food.pojos.ProductResponseDto;
import com.example.food.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    UserUtil userUtil;
    @Mock
    UserRepository userRepository;

    @Test
    void testSearchProduct() {
        //Setting up test data

        List<Product> expectedProducts = Arrays.asList(
                createNewProduct(1L,"apple1",290D),
                createNewProduct(2L,"apple2",290D),
                createNewProduct(3L,"apple3",290D)
        );
        Page<Product> expectedPage = new PageImpl<>(expectedProducts);
        //MOCKING THE BEHAVIOUR
        when(productRepository.findByProductNameContainingIgnoreCase( anyString(), any(Pageable.class)))
                .thenReturn(expectedPage);
        PaginatedProductResponse response = productServiceImpl.searchProduct("apple","productName","asc",0,1);
        assertSame(response.getProductList(), expectedPage);
        verify(productRepository).findByProductNameContainingIgnoreCase(any(String.class), any(Pageable.class));
    }

    private Product createNewProduct(final Long productId, final String productName, final Double productPrice) {
        Product product = new Product();
        product.setProductName(productName);
        product.setId(productId);
        product.setProductPrice(BigDecimal.valueOf(productPrice));
        product.setCategory(new Category());
        product.setDescription("sweet");
        product.setCreatedAt(new Date());
        product.setModifiedAt(new Date());
        return product;
    }

    @Test
    void testSearchProductWhenFilterIsBlank() {
        // Setting up test data
        List<Product> expectedProducts = Arrays.asList(
                createNewProduct(1L,"apple1",290D),
                createNewProduct(2L,"apple2",290D),
                createNewProduct(3L,"apple3",290D)
        );
        Page<Product> expectedPage = new PageImpl<>(expectedProducts);
        //MOCKING THE BEHAVIOUR
        when(productRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);
        //VERIFICATION OF THR MOCK BEHAVIOUR

        PaginatedProductResponse response = productServiceImpl.searchProduct("","productName","asc",0,1);
        assertSame(response.getProductList(), expectedPage);
        verify(productRepository).findAll((Pageable) any());
    }


    @Test
    void testSearchProductIsNull() {
        // Setting up test data
        Page<Product> expectedPage = new PageImpl<>(new ArrayList<>());
        //MOCKING THE BEHAVIOUR
        when(productRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        //VERIFICATION OF THR MOCK BEHAVIOUR
        PaginatedProductResponse response = productServiceImpl.searchProduct("","productName","asc",0,1);
        assertTrue(response.getProductList().getTotalElements()==0);
        verify(productRepository).findAll((Pageable) any());
    }

    @Test
    public void testFetchSingleProduct_success() {
        Product product = createNewProduct(1L,"apple1",290D);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductResponseDto response = productServiceImpl.fetchSingleProduct(1L);
        assertTrue(response.getDescription().startsWith("Success"));
    }

    @Test
    public void testFetchSingleProduct_Error() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        ProductResponseDto response = productServiceImpl.fetchSingleProduct(2L);
        assertTrue(response.getDescription().startsWith("Product not found"));
    }

    @Test
    public void updateProduct_withAdminRole_shouldReturnSuccessResponse() {
        // given
        Long productId = 1L;
        UpdateProductDto productDto = new UpdateProductDto();

        Users user = new Users();
        user.setEmail("test@email.com");
        user.setFirstName("Test_First_Name");
        user.setLastName("Test_Last_Name");
        user.setRole(Role.ROLE_ADMIN);

        Product product = new Product();

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        UpdatedProductResponse updatedProductResponse = productServiceImpl.updateProduct(productId, productDto);

        verify(productRepository, times(1)).save(product);
        assertEquals(updatedProductResponse.getDescription(), "null updated successfully");
    }

        @Test
    public void updateProduct_withNonAdminRole_shouldReturnUnauthorizedResponse() {

        Long productId = 1L;
        UpdateProductDto productDto = new UpdateProductDto();
        Users user = new Users();
        user.setRole(Role.ROLE_USER);

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));

        UpdatedProductResponse updatedProductResponse = productServiceImpl.updateProduct(productId, productDto);

        verify(productRepository, times(0)).save(any());
        assertEquals(updatedProductResponse.getDescription(), ResponseCodeEnum.UNAUTHORISED_ACCESS.getDescription());
    }
}
