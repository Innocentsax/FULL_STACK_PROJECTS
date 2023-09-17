package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.dto.CategoryDTO;
import com.decagon.safariwebstore.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepositoryUnderTest;

    @Autowired
    private ModelMapper modelMapper;

    @AfterEach
    void tearDown() {
        categoryRepositoryUnderTest.deleteAll();
    }

    @Test
    void shouldFindCategoryByName() {
        // given
        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
        categoryRepositoryUnderTest.save(clothes);
        Category shoes = modelMapper.map(new CategoryDTO("shoes"), Category.class);
        categoryRepositoryUnderTest.save(shoes);
        Category accessories = modelMapper.map(new CategoryDTO("accessories"), Category.class);
        categoryRepositoryUnderTest.save(accessories);

        // when
        Optional<Category> clothesCategory = categoryRepositoryUnderTest.findByName("clothes");
        Optional<Category> shoesCategory = categoryRepositoryUnderTest.findByName("shoes");
        Optional<Category> accessoriesCategory = categoryRepositoryUnderTest.findByName("accessories");

        // then
        assertAll(
                () -> assertTrue(clothesCategory.isPresent()),
                () -> assertTrue(shoesCategory.isPresent()),
                () -> assertTrue(accessoriesCategory.isPresent())
        );
    }
}
