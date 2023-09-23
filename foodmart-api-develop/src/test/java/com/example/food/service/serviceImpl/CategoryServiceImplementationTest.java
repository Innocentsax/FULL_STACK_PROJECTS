package com.example.food.service.serviceImpl;

import com.example.food.model.Category;
import com.example.food.dto.CategoryDto;
import com.example.food.pojos.CreateCategoryResponse;
import com.example.food.repositories.CategoryRepository;
import com.example.food.services.serviceImpl.CategoryServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplementationTest {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryServiceImplementation categoryService;
    Category category;
    CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        categoryDto = new CategoryDto();
    }

    @Test
    void createCategory() {
        when(categoryRepository.findByCategoryName(anyString()))
                .thenReturn(category);
        when(categoryRepository.save(category))
                .thenReturn(category);

        CreateCategoryResponse createCategoryResponse = categoryService.createCategory(categoryDto);
        assertEquals("New Category Added", createCategoryResponse.getDescription());
    }
}
