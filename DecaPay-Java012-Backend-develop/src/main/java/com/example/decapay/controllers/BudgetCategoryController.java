package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import com.example.decapay.services.BudgetCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/budgets/category")

public class BudgetCategoryController {

    private final BudgetCategoryService budgetCategoryService;

    @PostMapping("/create")
    public ResponseEntity<BudgetCategoryResponse> createBudgetCategory(@Valid @RequestBody BudgetCategoryRequest budgetCategoryRequest){
        BudgetCategoryResponse budgetCategoryResponse = budgetCategoryService.createBudgetCategory(budgetCategoryRequest);
        return ResponseEntity.ok(budgetCategoryResponse);
    }

    @PutMapping("/update/{budgetCategoryId}")
    public ResponseEntity<BudgetCategoryResponse> updateBudgetCategory(@Valid @PathVariable Long budgetCategoryId, @RequestBody BudgetCategoryRequest budgetCategoryRequest)
    {
      BudgetCategoryResponse  budgetCategoryResponse=  budgetCategoryService.updateBudgetCategory(budgetCategoryId,budgetCategoryRequest);
      return  ResponseEntity.ok(budgetCategoryResponse);
    }

    @GetMapping("/{budgetCategoryId}")
    public ResponseEntity<BudgetCategoryResponse> getBudgetCategory(@Valid @PathVariable Long budgetCategoryId) {
        BudgetCategoryResponse  budgetCategoryResponse =  budgetCategoryService.getBudgetCategory(budgetCategoryId);
        return  ResponseEntity.ok(budgetCategoryResponse);
    }

    @GetMapping
    public ResponseEntity<List<BudgetCategoryResponse>> getBudgetCategories() {
        List<BudgetCategoryResponse>  budgetCategoryResponses =  budgetCategoryService.getBudgetCategories();
        return  ResponseEntity.ok(budgetCategoryResponses);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteBudgetCategory(@PathVariable Long categoryId){
        budgetCategoryService.deleteBudgetCategory(categoryId);
        return ResponseEntity.ok("Budget Category successfully deleted");
    }
}
