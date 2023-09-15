package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private ProductServiceImpl underTest;

    private Product product;
    private ViewProductDto viewProductDto;
    private SubCategory subCategory;

    @BeforeEach
    void setUp(){
        List<SubCategory> subCategories = new ArrayList<>();
        subCategories.add(subCategory);

        Category category = Category.builder()
                .categoryName("")
                .build();

        subCategory = SubCategory.builder()
                .subCategoryName("")
                .category(category)
                .build();

        viewProductDto = ViewProductDto.builder()
                .productName("dining_set")
                .description("best_in_town")
                .price(400.00)
                .build();

        product = new Product();
        product.setId(9L);
        product.setProductName(viewProductDto.getProductName());
    }

    @Test
    void shouldGetASingleProduct() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(mapper.map(product, ViewProductDto.class)).thenReturn(viewProductDto);

        ResponseEntity<ViewProductDto> response = underTest.getASingleProduct(product.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(viewProductDto);
    }
}
