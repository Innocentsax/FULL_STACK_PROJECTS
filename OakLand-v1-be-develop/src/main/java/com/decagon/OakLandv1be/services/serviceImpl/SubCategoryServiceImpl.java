package com.decagon.OakLandv1be.services.serviceImpl;


import com.decagon.OakLandv1be.dto.SubCategoryDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.EmptyListException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.CategoryRepository;
import com.decagon.OakLandv1be.repositries.SubCategoryRepository;
import com.decagon.OakLandv1be.services.SubCategoryService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ApiResponse<SubCategory> createSubCategory(SubCategoryDto subCategoryDto, Long category_id) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exist"));
        if (subCategoryRepository.existsByName(subCategoryDto.getName()))
            throw new AlreadyExistsException("Kindly create another subCategory with a different name");
        SubCategory subCategory = SubCategory.builder()
                .name(subCategoryDto.getName().toUpperCase())
                .category(category)
                .imageUrl(subCategoryDto.getImageUrl())
                .build();
        subCategoryRepository.save(subCategory);
        return new ApiResponse<>("SubCategory Created Successfully", true, subCategory);
    }

    @Override
    public List<SubCategoryDto> viewAllSubCategories() {
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        if(subCategoryList.isEmpty())
            throw new EmptyListException("There are no SubCategories yet", "Kindly create categories");
        List<SubCategoryDto> subCategoryDtos = new ArrayList<>();
        subCategoryList.forEach(subCategory -> {
            SubCategoryDto subCategoryDto = SubCategoryDto.builder().name(subCategory.getName()).imageUrl(subCategory.getImageUrl()).build();
            subCategoryDtos.add(subCategoryDto);
        });
        return subCategoryDtos;
    }

    @Override
    public void deleteSubCategory(Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() ->
                new ResourceNotFoundException("SubCategory with the name does not exist"));
        subCategoryRepository.delete(subCategory);
    }

    @Override
    public ApiResponse<SubCategory> editSubCategory(SubCategoryDto subCategoryDto, Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() ->
                new ResourceNotFoundException("This SubCategory does not exist"));
        subCategory.setName(subCategoryDto.getName());
        subCategory.setImageUrl(subCategoryDto.getImageUrl());
        return new ApiResponse<>("SubCategory Updated successfully", true, subCategoryRepository.save(subCategory));
    }

    @Override
    public Set<SubCategoryDto> ViewSubCategoryByCategory(Long category_id) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exist"));
        Set<SubCategory> subCategoryList = category.getSubCategories();
        Set<SubCategoryDto> subCategoryDtos = new HashSet<>();
        subCategoryList.forEach(subCategory -> {
            SubCategoryDto subCategoryDto = SubCategoryDto.builder()
                    .id(subCategory.getId())
                    .size(subCategory.getProducts().size())
                    .imageUrl(subCategory.getImageUrl())
                    .name(subCategory.getName()).build();
            subCategoryDtos.add(subCategoryDto);
        });
        return subCategoryDtos;
    }

}
