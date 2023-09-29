package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.CategoryDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.EmptyListException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.CategoryRepository;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryServiceImpl categoryService;

    private Category category;
    private Category updatedCategory;
    private Category category1;
    private CategoryDto categoryDto;
    private List<Category> categoryList;
    private List<CategoryDto> categoryDtoList;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = Category.builder().name("Tester").imageUrl("image.com").build();
        category1 = Category.builder().name("Tester").imageUrl("image.com").build();
        category.setId(3L);
        categoryDto = CategoryDto.builder().name("Tester").imageUrl("image.com").build();
        SubCategory subCategory = SubCategory.builder()
                .name("Bed")
                .imageUrl("subcat.com")
                .category(category)
                .build();
        categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category1);
        when(categoryRepository.existsByName(anyString())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(category);
    }

    @Test
    void createCategory() {
        ApiResponse<Category> response = categoryService.createCategory(categoryDto);
        assertTrue(response.getStatus());
        assertEquals("Category Created Successfully", response.getMessage());
        assertEquals(category.getName(), response.getData().getName());
        assertEquals(category.getImageUrl(), response.getData().getImageUrl());
//        verify(categoryRepository, times(1)).save(category);
    }
    @Test
    void createCategoryShouldThrowAlreadyExistsException(){
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> categoryService.createCategory(categoryDto));
        verify(categoryRepository, times(1)).existsByName(categoryDto.getName());
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    void deleteCategory() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        categoryService.deleteCategory(2L);
        verify(categoryRepository, times(1)).findById(2L);
        verify(categoryRepository, times(1)).delete(category);
    }
    @Test
    void shouldThrowResourceNotFoundException(){
        when(categoryRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(3L));
        assertThrows(ResourceNotFoundException.class, () -> categoryService.editCategory(categoryDto, 2L));
        assertThrows(ResourceNotFoundException.class, () -> categoryService.fetchASingleCategory(3L));
        verify(categoryRepository, times(0)).delete(any(Category.class));
    }
    @Test
    void editCategory() {
        updatedCategory =  Category.builder().name("Book").imageUrl("subcat.com").build();;
        updatedCategory.setId(2L);
        when(categoryRepository.findById(3L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(updatedCategory);
        ApiResponse<Category> response = categoryService.editCategory(categoryDto, 3L);
        assertTrue(response.getStatus());
        assertEquals("Category Updated successfully", response.getMessage());
        assertEquals(updatedCategory, response.getData());
        verify(categoryRepository, times(1)).findById(3L);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void fetchASingleCategory() {
        when(categoryRepository.findById(3L)).thenReturn(Optional.of(category));
        Category fetchedCategory = categoryService.fetchASingleCategory(3L);
        assertEquals(category, fetchedCategory);
        verify(categoryRepository, times(1)).findById(3L);
    }

    @Test
    void viewAllCategoriesShouldThrowException(){
        List<Category> categoryList = Collections.emptyList();
        when(categoryRepository.findAll()).thenReturn(categoryList);
        assertThrows(EmptyListException.class, () -> categoryService.viewAllCategories());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void viewAllCategories() {
        categoryDtoList = Arrays.asList(CategoryDto.builder().name("Tester").imageUrl("image.com").build(),
                CategoryDto.builder().name("Tester").imageUrl("image.com").build());
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDto> actualCategoryDtoList = categoryService.viewAllCategories();
        assertEquals(categoryDtoList, actualCategoryDtoList);
        verify(categoryRepository, times(1)).findAll();
    }


    @Test
    void viewAllCategoriesDeviation_NoCategories_ShouldThrowException() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(EmptyListException.class, () -> categoryService.viewAllCategoriesDeviation());
        verify(categoryRepository).findAll();
    }
}