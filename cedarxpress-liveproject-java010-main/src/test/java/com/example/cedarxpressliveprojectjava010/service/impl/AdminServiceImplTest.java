package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.ImageUrl;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import com.example.cedarxpressliveprojectjava010.repository.CategoryRepository;
import com.example.cedarxpressliveprojectjava010.repository.ImageUrlRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.SubCategoryRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @InjectMocks
    private AdminServiceImpl adminService;
    @Mock
    private  CategoryRepository categoryRepository;
    @Mock
    private  SubCategoryRepository subCategoryRepository;
    @Mock
    private  ProductRepository productRepository;
    @Mock
    private  ImageUrlRepository imageUrlRepository;

    @Mock
    private ModelMapper modelMapper;


    ProductDto productDto;
    Product product;
    Category category;
    SubCategory subCategory;
    ImageUrl imgUrl;

    @BeforeEach
    void setUp() {

        productDto = ProductDto.builder()
                .productName("reading chair")
                .description("egronomic chair to rest the neck")
                .category("office")
                .subCategory("wooden")
                .price(120000)
                .build();
        category = Category.builder()
                .categoryName("office").build();
        subCategory = SubCategory.builder()
                .subCategoryName("wooden")
                .build();
        product = Product.builder()
                .productName("reading chair")
                .description("egronomic chair to rest the neck")
                .category(category)
                .subCategory(subCategory)
                .price(120000)
                .build();
        product.setId(1L);
        imgUrl = ImageUrl.builder().img("furniture1.jpeg").build();
    }

    @Test
    void createProduct() {
        given(categoryRepository.findCategoryByCategoryName(productDto.getCategory())).willReturn(Optional.of(category));
        given(subCategoryRepository.findSubCategoryBySubCategoryName(productDto.getSubCategory())).willReturn(Optional.of(subCategory));
        given(productRepository.save(any())).willReturn(product);
        ResponseEntity<Product> response = adminService.createProduct(productDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(product);
    }

    @Test
    void addProductImage() {
        String img = "furniture1.jpeg";
        List<ImageUrl> images = new ArrayList<>();
        images.add(imgUrl);
        product.setImages(images);
        given(productRepository.findById(1l)).willReturn(Optional.of(product));
        given(imageUrlRepository.save(any())).willReturn(imgUrl);
        ResponseEntity<Product> response = adminService.addProductImage(img, 1l);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(product);
    }

    @Test
    void deleteProduct(){

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        ResponseEntity<String> response = adminService.deleteProduct(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Product with id "+1L+" deleted successfully");
    }

    @Test
    void updateProduct() {
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(categoryRepository.findCategoryByCategoryName(productDto.getCategory())).willReturn(Optional.of(category));
        given(subCategoryRepository.findSubCategoryBySubCategoryName(productDto.getSubCategory())).willReturn(Optional.of(subCategory));
        product.setDescription("New Persian Door");
        product.setPrice(1500000);
        given(productRepository.save(any())).willReturn(product);

        productDto.setDescription("New Persian Door");
        productDto.setPrice(1500000);
        ProductDto testUpdatedProduct = adminService.updateProduct(productDto, product.getId());

        assertThat(testUpdatedProduct.getDescription()).isEqualTo(productDto.getDescription());
        assertThat(testUpdatedProduct.getPrice()).isEqualTo(productDto.getPrice());
    }
}