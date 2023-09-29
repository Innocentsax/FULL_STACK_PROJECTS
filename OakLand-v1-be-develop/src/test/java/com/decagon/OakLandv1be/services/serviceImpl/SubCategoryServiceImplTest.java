package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.CategoryDto;
import com.decagon.OakLandv1be.dto.SubCategoryDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.EmptyListException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.CategoryRepository;
import com.decagon.OakLandv1be.repositries.SubCategoryRepository;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SubCategoryServiceImplTest {
    @Mock
    SubCategoryRepository subCategoryRepository;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    SubCategoryServiceImpl subCategoryService;

    private Category category;
    private SubCategory subCategory;
    private SubCategory subCategory1;
    private SubCategory updatedSubCategory;
    private SubCategoryDto subCategoryDto;
    private List<SubCategory> subCategoryList;
    private List<SubCategoryDto> subCategoryDtos;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = Category.builder().name("Tester").build();
        category.setId(3L);
        subCategory = SubCategory.builder().name("Mini Tester").category(category).build();
        subCategory.setId(10L);
        subCategory1 = SubCategory.builder().name("Flash").category(category).build();
        subCategoryDto = SubCategoryDto.builder().name("Mini Tester").build();
        subCategoryList = new ArrayList<>();
        subCategoryList.add(subCategory);
        subCategoryList.add(subCategory1);
    }

    @Test
    void createSubCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(subCategoryRepository.existsByName("Mini Tester")).thenReturn(false);
        when(subCategoryRepository.save(subCategory)).thenReturn(subCategory);
        ApiResponse<SubCategory> response = subCategoryService.createSubCategory(subCategoryDto, 3L);
        assertTrue(response.getStatus());
        assertEquals("SubCategory Created Successfully", response.getMessage());
        assertEquals(subCategory.getName(), response.getData().getName());
        assertEquals(subCategory.getCategory(), response.getData().getCategory());
        verify(categoryRepository, times(1)).findById(3L);
        verify(subCategoryRepository, times(1)).existsByName(subCategoryDto.getName());
    }

    @Test
    void createCategoryShouldThrowAlreadyExistsException(){
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(subCategoryRepository.existsByName("Mini Tester")).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> subCategoryService.createSubCategory(subCategoryDto, 3L));
        verify(subCategoryRepository, times(1)).existsByName("Mini Tester");
        verify(subCategoryRepository, times(0)).save(any(SubCategory.class));
        verify(categoryRepository, times(1)).findById(3L);
        verify(subCategoryRepository, times(1)).existsByName(subCategoryDto.getName());
    }
     @Test
        void shouldThrowResourceNotFoundException(){
         when(categoryRepository.findById(3L)).thenReturn(Optional.empty());
         assertThrows(ResourceNotFoundException.class, () -> subCategoryService.createSubCategory(subCategoryDto, 3L));
         assertThrows(ResourceNotFoundException.class, () -> subCategoryService.deleteSubCategory(10L));
         assertThrows(ResourceNotFoundException.class, () -> subCategoryService.editSubCategory(subCategoryDto, 10L));
         assertThrows(ResourceNotFoundException.class, () -> subCategoryService.ViewSubCategoryByCategory(3L));
         verify(categoryRepository, times(0)).delete(any(Category.class));
     }
    @Test
    void viewAllSubCategories() {
        subCategoryDtos = Arrays.asList(SubCategoryDto.builder().name("Mini Tester").build(),
                SubCategoryDto.builder().name("Mini Tester").build());
        when(subCategoryRepository.findAll()).thenReturn(subCategoryList);
        when(subCategoryRepository.findAll()).thenReturn(subCategoryList);
        List<SubCategoryDto> subCategoryDtos = subCategoryService.viewAllSubCategories();
        assertEquals(subCategoryDtos, subCategoryDtos);
        verify(subCategoryRepository, times(1)).findAll();
    }

    @Test
    public void testViewAllSubCategories_EmptyList() {
        List<SubCategory> subCategoryList = new ArrayList<>();
        when(subCategoryRepository.findAll()).thenReturn(subCategoryList);
        assertThrows(EmptyListException.class, () -> subCategoryService.viewAllSubCategories());
        verify(subCategoryRepository, times(1)).findAll();
    }
    @Test
    void deleteSubCategory() {
        when(subCategoryRepository.findById(10L)).thenReturn(Optional.of(subCategory));
        doNothing().when(subCategoryRepository).delete(subCategory);
        subCategoryService.deleteSubCategory(10L);
        verify(subCategoryRepository, times(1)).findById(10L);
        verify(subCategoryRepository, times(1)).delete(subCategory);
    }

    @Test
    void editSubCategory() {
        updatedSubCategory = SubCategory.builder().name("Books and Magazines").build();
        updatedSubCategory.setId(10L);
        when(subCategoryRepository.findById(10L)).thenReturn(Optional.of(subCategory));
        when(subCategoryRepository.save(subCategory)).thenReturn(updatedSubCategory);
        ApiResponse<SubCategory> response = subCategoryService.editSubCategory(subCategoryDto, 10L);
        assertTrue(response.getStatus());
        assertEquals("SubCategory Updated successfully", response.getMessage());
        assertEquals(updatedSubCategory, response.getData());
        verify(subCategoryRepository, times(1)).findById(10L);
        verify(subCategoryRepository, times(1)).save(subCategory);
    }
}