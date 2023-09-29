package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.SubCategoryDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.services.SubCategoryService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subcategory")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @PostMapping("/admin/new/{category_id}")
    ResponseEntity<ApiResponse<SubCategory>> addNewSubCategory(@Valid @RequestBody SubCategoryDto subCategoryDto,
                                                  @PathVariable("category_id") Long category_id) {
        return new ResponseEntity<>(subCategoryService.createSubCategory(subCategoryDto, category_id), HttpStatus.CREATED);
    }
    @GetMapping("/view-all")
    ResponseEntity<List<SubCategoryDto>> viewAllSubCategories (){
        List<SubCategoryDto> subCategoryDtoList = subCategoryService.viewAllSubCategories();
        return new ResponseEntity<>(subCategoryDtoList, HttpStatus.OK);
    }

    @GetMapping("/viewByCategory/{category_id}")
    public ResponseEntity<Set<SubCategoryDto>> viewAllSubcategoriesInACategory(@PathVariable("category_id") Long category_id){
        Set<SubCategoryDto> subCategoryDtos = subCategoryService.ViewSubCategoryByCategory(category_id);
        return new ResponseEntity<>(subCategoryDtos, HttpStatus.OK);
    }

    @PatchMapping("/admin/update/{subCategoryId}")
    public ResponseEntity<ApiResponse<SubCategory>> updateSubCategory(@PathVariable("subCategoryId") Long subCategoryId,
                                                                   @Valid @RequestBody SubCategoryDto subCategoryDto) {
        return new ResponseEntity<>(subCategoryService.editSubCategory(subCategoryDto, subCategoryId), HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{subCategoryId}")
    public ResponseEntity<String> deleteSubCategory(@PathVariable("subCategoryId") Long subCategoryId) {
        subCategoryService.deleteSubCategory(subCategoryId);
        return new ResponseEntity<>("SubCategory successfully deleted", HttpStatus.NO_CONTENT);
    }
}
