package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.CategoryDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.EmptyListException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.CategoryRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.repositries.SubCategoryRepository;
import com.decagon.OakLandv1be.services.CategoryService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public ApiResponse<Category> createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName()))
            throw new AlreadyExistsException("Please create another category with a different name");
        Category category = Category.builder()
                .name(categoryDto.getName().toUpperCase())
                .imageUrl(categoryDto.getImageUrl())
                .build();
        categoryRepository.save(category);
        return new ApiResponse<>("Category Created Successfully", true, category);
    }

    @Override
    public List<CategoryDto> viewAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        if(categoryList.isEmpty())
            throw new EmptyListException("There are no categories yet", "Kindly create categories");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryList.forEach(category -> {
            CategoryDto categoryDto = CategoryDto.builder().name(category.getName()).imageUrl(category.getImageUrl()).build();
            categoryDtoList.add(categoryDto);
        });
        return categoryDtoList;
    }

    @Override
    public void deleteCategory(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() ->
           new ResourceNotFoundException("Category with the name does not exist"));
        categoryRepository.delete(category);
    }

    @Override
    public ApiResponse<Category> editCategory(CategoryDto categoryDto, Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() ->
                new ResourceNotFoundException("This Category does not exist"));
        category.setName(categoryDto.getName());
        category.setImageUrl(category.getImageUrl());
        Category updatedCategory = categoryRepository.save(category);
        return new ApiResponse<>("Category Updated successfully", true, updatedCategory);
    }

    @Override
    public Category fetchASingleCategory(Long category_id) {
        return categoryRepository.findById(category_id).orElseThrow(() ->
                new ResourceNotFoundException("This Category does not exist"));
    }

    @Override
    public List<CategoryDto> viewAllCategoriesDeviation() {
       List<Category> categoryList = categoryRepository.findAll();
        if(categoryList.isEmpty())
            throw new EmptyListException("There are no categories yet", "Kindly create categories");

        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryList.forEach(category -> {
            int size2 = category.getSubCategories().size();
            CategoryDto categoryDto = CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .imageUrl(category.getImageUrl())
                    .size(size2)
                    .build();
            categoryDtoList.add(categoryDto);
        });
        return categoryDtoList;
    }
}