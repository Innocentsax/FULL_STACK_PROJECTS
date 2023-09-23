package com.example.food.controllers;

import com.example.food.dto.CategoryDto;
import com.example.food.pojos.CreateCategoryResponse;
import com.example.food.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-category")
    public ResponseEntity<CreateCategoryResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CreateCategoryResponse categoryResponse = (CreateCategoryResponse) categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }
}
