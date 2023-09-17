package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.dto.CategoryDTO;
import com.decagon.safariwebstore.dto.SubCategoryDTO;
import com.decagon.safariwebstore.model.Category;
import com.decagon.safariwebstore.model.SubCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubCategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepositoryUnderTest;

    @Autowired
    private SubCategoryRepository subCategoryRepositoryUnderTest;

    @Autowired
    private ModelMapper modelMapper;

    @AfterEach
    void tearDown() {
        subCategoryRepositoryUnderTest.deleteAll();
        categoryRepositoryUnderTest.deleteAll();
    }

    @Test
    void findByNameAndCategory() {
        // given
        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
        categoryRepositoryUnderTest.save(clothes);
        Category shoes = modelMapper.map(new CategoryDTO("shoes"), Category.class);
        categoryRepositoryUnderTest.save(shoes);
        Category accessories = modelMapper.map(new CategoryDTO("accessories"), Category.class);
        categoryRepositoryUnderTest.save(accessories);

        SubCategory dresses = modelMapper.map(new SubCategoryDTO("dresses", clothes), SubCategory.class);
        subCategoryRepositoryUnderTest.save(dresses);
        SubCategory denim = modelMapper.map(new SubCategoryDTO("denim", clothes), SubCategory.class);
        subCategoryRepositoryUnderTest.save(denim);
        SubCategory flats = modelMapper.map(new SubCategoryDTO("flats", shoes), SubCategory.class);
        subCategoryRepositoryUnderTest.save(flats);
        SubCategory watches = modelMapper.map(new SubCategoryDTO("watches", accessories), SubCategory.class);
        subCategoryRepositoryUnderTest.save(watches);

        // when
        Optional<SubCategory> clothesSubcategory = subCategoryRepositoryUnderTest
                .findByName("dresses");
        Optional<SubCategory> clothesSubcategory1 = subCategoryRepositoryUnderTest
                .findByName("denim");
        Optional<SubCategory> shoesSubcategory = subCategoryRepositoryUnderTest
                .findByName("flats");
        Optional<SubCategory> accessoriesSubcategory = subCategoryRepositoryUnderTest
                .findByName("watches");

        // then
        assertAll(
                () -> assertTrue(clothesSubcategory.isPresent()),
                () -> assertTrue(clothesSubcategory1.isPresent()),
                () -> assertTrue(shoesSubcategory.isPresent()),
                () -> assertTrue(accessoriesSubcategory.isPresent())
        );

    }
}
