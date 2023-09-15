package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.config.CloudinaryConfig;
import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.service.impl.InitializeTransactionServiceImpl;
import com.example.cedarxpressliveprojectjava010.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper mapper;
    @MockBean
    private CloudinaryService cloudinaryService;
    @MockBean
    private InitializeTransactionServiceImpl paymentService;

    @MockBean
    private CloudinaryConfig cloudinaryConfig;



    private Product product;
    private Product product1;
    private Product product2;

    Category category = new Category("Table");
    List<Product> productList = null;
    Page<Product> productPage;
    ViewProductDto viewProductDto;
    List<ViewProductDto> viewProductDtoList;


    @BeforeEach
    public void setUp(){
        product = Product.builder()
                .productName("Pablo")
                .category(category)
                .description("Black ceder wood table")
                .price(20500.00)
                .subCategory(SubCategory.builder()
                        .subCategoryName("Ceder Tables")
                        .category(category)
                        .build()).build();
        product1 = Product.builder()
                .productName("Ablo")
                .category(category)
                .description("White ceder wood table")
                .price(25000.00)
                .subCategory(SubCategory.builder()
                        .subCategoryName("Ceder Tables")
                        .category(category)
                        .build()).build();
        product2 = Product.builder()
                .productName("Mablo")
                .category(category)
                .description("Red ceder wood table")
                .price(29500.00)
                .subCategory(SubCategory.builder()
                        .subCategoryName("Ceder Tables")
                        .category(category)
                        .build())
                .build();

        productList = new ArrayList<>();
        productList.add(product);

        productPage = new PageImpl<>(productList);

        viewProductDtoList = new ArrayList<>();


        viewProductDto = ViewProductDto.builder()
                .productName("any")
                .price(29500.0)
                .build();

        viewProductDtoList.add(viewProductDto);
    }


    @Test
    @DisplayName("get all products")
    void shouldGetAllProductsAsADto() {
        String keyword = "A";

//        given(productRepository.findAll(anyString(),any())).willReturn(product2);
////        given(mapper.map(any(), any())).willReturn(viewProductDto);
//
//        List<Product> prod = productService.fetchAllProducts(0, 2, "asc", keyword);
//
//        assertThat(prod).isEqualTo(viewProductDtoList);
    }
}