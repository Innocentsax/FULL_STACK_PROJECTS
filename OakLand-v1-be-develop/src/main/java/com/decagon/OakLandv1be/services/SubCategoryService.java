package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.SubCategoryDto;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.utils.ApiResponse;

import java.util.List;
import java.util.Set;

public interface SubCategoryService {
    ApiResponse<SubCategory> createSubCategory(SubCategoryDto subCategoryDto, Long category_id);
    List<SubCategoryDto> viewAllSubCategories();
    void deleteSubCategory(Long subCategoryId);
    ApiResponse<SubCategory> editSubCategory(SubCategoryDto subCategoryDto, Long subCategoryId);
    Set<SubCategoryDto> ViewSubCategoryByCategory(Long category_id);
}
