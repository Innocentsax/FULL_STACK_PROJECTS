//package com.example.cedarxpressliveprojectjava010.controller;
//
//import com.example.cedarxpressliveprojectjava010.config.CloudinaryConfig;
//import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
//import com.example.cedarxpressliveprojectjava010.entity.Category;
//import com.example.cedarxpressliveprojectjava010.entity.Product;
//import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
//import com.example.cedarxpressliveprojectjava010.service.CloudinaryService;
//import com.example.cedarxpressliveprojectjava010.service.ProductService;
//import com.example.cedarxpressliveprojectjava010.util.AppConstants;
//import com.example.cedarxpressliveprojectjava010.util.ProductResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import java.util.ArrayList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@SpringBootTest
//class ProductControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    ProductService productService;
//
//    @MockBean
//    CloudinaryService cloudinaryService;
//
//    @MockBean
//    CloudinaryConfig cloudinaryConfig;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    ResponseEntity<ProductResponse> productResponseEntity;
//
//
//    private Product product;
//    private ProductDto productDto;
//    private SubCategory subCategory;
//
//
//    @BeforeEach
//    void setUp() {
//        List<SubCategory> subCategories = new ArrayList<>();
//        subCategories.add(subCategory);
//
//        Category category = Category.builder()
//                .categoryName("")
//                .build();
//
//        subCategory = SubCategory.builder()
//                .subCategoryName("")
//                .category(category)
//                .build();
//
//        productDto = ProductDto.builder()
//                .productName("dining_set")
//                .description("best_in_town")
//                .price(400.00)
//                .subCategory(subCategory.getSubCategoryName())
//                .category(category.getCategoryName())
//                .build();
//
//        product = new Product();
//        product.setId(9L);
//        product.setProductName(productDto.getProductName());
//
//        List<Product> productsList = new ArrayList<>();
//        productsList.add(product);
//        Page<Product> productPage = new PageImpl<>(productsList);
//        List<ProductDto> content = new ArrayList<>();
//        content.add(productDto);
//        ProductResponse productResponse = ProductResponse.builder()
//                .content(content)
//                .pageNo(productPage.getNumber())
//                .pageSize(productPage.getSize())
//                .totalPages(productPage.getTotalPages())
//                .totalElements(productPage.getTotalElements())
//                .last(productPage.isLast())
//                .build();
//        productResponseEntity = ResponseEntity.ok(productResponse);
//    }
//
//    @Test
//    void whenGetAllProductsContIsHitItShouldBeWithTheCorrectMethodGet() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/products/all")
//                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//    }
//
//
//    @Test
//    void whenGetAllProductsControllerIsHitWithWrongMethodShouldThrow500() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/products/all")
//                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
//    }
//
//    @Test
//    void businessLogicIsCalledWhenTheUrlIsHit() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/products/all").param("pageNo", AppConstants.DEFAULT_PAGE_NUMBER)
//                .param("pageSize", AppConstants.DEFAULT_PAGE_SIZE).accept(MediaType.APPLICATION_JSON)).
//                andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
//        assertEquals(integerArgumentCaptor.getValue(), 10);
//    }
//
//}