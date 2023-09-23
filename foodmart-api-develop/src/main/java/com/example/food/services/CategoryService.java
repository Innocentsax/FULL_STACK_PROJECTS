package com.example.food.services;

import com.example.food.dto.CategoryDto;
import com.example.food.restartifacts.BaseResponse;

//@Service
public interface CategoryService {
     BaseResponse createCategory(CategoryDto categoryDto);
}
