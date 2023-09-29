package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.CategoryDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.utils.ApiResponse;

import java.util.List;

public interface CategoryService {
    ApiResponse<Category> createCategory(CategoryDto categoryDto);
    List<CategoryDto> viewAllCategories();
    void deleteCategory(Long category_id);

    ApiResponse<Category> editCategory(CategoryDto categoryDto, Long category_id);
    Category fetchASingleCategory(Long category_id);

    List<CategoryDto> viewAllCategoriesDeviation();
}
