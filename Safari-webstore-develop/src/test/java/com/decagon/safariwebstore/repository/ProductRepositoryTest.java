//package com.decagon.safariwebstore.repository;
//
//import com.decagon.safariwebstore.model.*;
//import com.decagon.safariwebstore.utils.MethodUtils;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class ProductRepositoryTest {
//
//    @Autowired
//    private ProductRepository productRepositoryUnderTest;
//
//    @Autowired
//    private CategoryRepository categoryRepositoryUnderTest;
//
//    @Autowired
//    private SubCategoryRepository subCategoryRepositoryUnderTest;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @AfterEach
//    void tearDown() {
//        productRepositoryUnderTest.deleteAll();
//        subCategoryRepositoryUnderTest.deleteAll();
//        categoryRepositoryUnderTest.deleteAll();
//    }
//
//    @Test
//    void shouldFindAllProductsByCategory() {
//        // given
//        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
//        categoryRepositoryUnderTest.save(clothes);
//        Category shoes = modelMapper.map(new CategoryDTO("shoes"), Category.class);
//        categoryRepositoryUnderTest.save(shoes);
//        Category accessories = modelMapper.map(new CategoryDTO("accessories"), Category.class);
//        categoryRepositoryUnderTest.save(accessories);
//
//        SubCategory dresses = modelMapper.map(new SubCategoryDTO("dresses", clothes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(dresses);
//        SubCategory denim = modelMapper.map(new SubCategoryDTO("denim", clothes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(denim);
//        SubCategory flats = modelMapper.map(new SubCategoryDTO("flats", shoes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(flats);
//        SubCategory watches = modelMapper.map(new SubCategoryDTO("watches", accessories), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(watches);
//
//        ProductDTO product1 = new ProductDTO("Smart watch", 132738,
//                "luxury watch for men", accessories, watches);
//        productRepositoryUnderTest.save(modelMapper.map(product1, Product.class));
//        ProductDTO product2 = new ProductDTO("Rebook flat show", 7482,
//                "Cool shoe for all seasons", shoes, flats);
//        productRepositoryUnderTest.save(modelMapper.map(product2, Product.class));
//        ProductDTO product3 = new ProductDTO("D&G jump suit", 42738,
//                "Beautiful piece for ladies", clothes, dresses);
//        productRepositoryUnderTest.save(modelMapper.map(product3, Product.class));
//        ProductDTO product4 = new ProductDTO("Skirt suit", 17938,
//                "Lovely 2 piece suit for ladies", clothes, denim);
//        productRepositoryUnderTest.save(modelMapper.map(product4, Product.class));
//
//        ProductPage productPage = new ProductPage();
//        Pageable pageable = MethodUtils.getPageable(productPage);
//
//        // when
//        Page<Product> productsClothes = productRepositoryUnderTest.findAllByCategory(clothes, pageable);
//        long expectedClothes = productsClothes.getTotalElements();
//
//        Page<Product> productsShoes = productRepositoryUnderTest.findAllByCategory(shoes, pageable);
//        long expectedShoes = productsShoes.getTotalElements();
//
//        Page<Product> productsAccessories = productRepositoryUnderTest.findAllByCategory(accessories, pageable);
//        long expectedAccessories = productsAccessories.getTotalElements();
//
//        // then
//        assertAll(
//                () -> assertEquals(expectedClothes, 2L),
//                () -> assertEquals(expectedShoes, 1L),
//                () -> assertEquals(expectedAccessories, 1L)
//        );
//    }
//
//    @Test
//    void shouldFindAllByCategoryAndSubCategory() {
//        // given
//        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
//        categoryRepositoryUnderTest.save(clothes);
//        Category shoes = modelMapper.map(new CategoryDTO("shoes"), Category.class);
//        categoryRepositoryUnderTest.save(shoes);
//        Category accessories = modelMapper.map(new CategoryDTO("accessories"), Category.class);
//        categoryRepositoryUnderTest.save(accessories);
//
//        SubCategory dresses = modelMapper.map(new SubCategoryDTO("dresses", clothes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(dresses);
//        SubCategory denim = modelMapper.map(new SubCategoryDTO("denim", clothes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(denim);
//        SubCategory flats = modelMapper.map(new SubCategoryDTO("flats", shoes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(flats);
//        SubCategory watches = modelMapper.map(new SubCategoryDTO("watches", accessories), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(watches);
//
//        ProductDTO product1 = new ProductDTO("Smart watch", 132738,
//                "luxury watch for men", accessories, watches);
//        productRepositoryUnderTest.save(modelMapper.map(product1, Product.class));
//        ProductDTO product2 = new ProductDTO("Rebook flat show", 7482,
//                "Cool shoe for all seasons", shoes, flats);
//        productRepositoryUnderTest.save(modelMapper.map(product2, Product.class));
//        ProductDTO product3 = new ProductDTO("D&G jump suit", 42738,
//                "Beautiful piece for ladies", clothes, dresses);
//        productRepositoryUnderTest.save(modelMapper.map(product3, Product.class));
//        ProductDTO product4 = new ProductDTO("Skirt suit", 17938,
//                "Lovely 2 piece suit for ladies", clothes, denim);
//        productRepositoryUnderTest.save(modelMapper.map(product4, Product.class));
//
//        ProductPage productPage = new ProductPage();
//        Pageable pageable = MethodUtils.getPageable(productPage);
//
//        // when
//        Page<Product> clothesDresses = productRepositoryUnderTest
//                .findAllByCategoryAndSubCategory(clothes, dresses, pageable);
//        long expectedClothesDresses = clothesDresses.getTotalElements();
//
//        Page<Product> clothesDenim = productRepositoryUnderTest
//                .findAllByCategoryAndSubCategory(clothes, denim, pageable);
//        long expectedClothesDenim = clothesDenim.getTotalElements();
//
//        Page<Product> shoesFlats = productRepositoryUnderTest
//                .findAllByCategoryAndSubCategory(shoes, flats, pageable);
//        long expectedShoesFlats = shoesFlats.getTotalElements();
//
//        Page<Product> accessoriesWatch = productRepositoryUnderTest
//                .findAllByCategoryAndSubCategory(accessories, watches, pageable);
//        long expectedAccessoriesWatch = accessoriesWatch.getTotalElements();
//
//        // then
//        assertAll(
//                () -> assertEquals(expectedClothesDresses, 1L),
//                () -> assertEquals(expectedClothesDenim, 1L),
//                () -> assertEquals(expectedShoesFlats, 1L),
//                () -> assertEquals(expectedAccessoriesWatch, 1L)
//        );
//    }
//}