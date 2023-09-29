package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.CategoryDto;
import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.services.CategoryService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/admin/new")
    ResponseEntity<ApiResponse<Category>> addNewCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/view-all")
    ResponseEntity<List<CategoryDto>> viewAllCategories (){
        List<CategoryDto> categoryDtoList = categoryService.viewAllCategories();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<Category> viewASingleCategory(@PathVariable("category_id") Long category_id){
        return new ResponseEntity<>(categoryService.fetchASingleCategory(category_id), HttpStatus.OK);
    }

    @PutMapping("/admin/update/{category_id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable("category_id") Long category_id,
                                                                   @Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.editCategory(categoryDto, category_id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{category_id}")
    public ResponseEntity<String> deleteCategory(@Valid @PathVariable("category_id") Long category_id){
        categoryService.deleteCategory(category_id);
        return new ResponseEntity<>("category successfully deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    ResponseEntity<List<CategoryDto>> viewAllCategoriesDeviation (){
        List<CategoryDto> categoryDtoList = categoryService.viewAllCategoriesDeviation();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }
}
