package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;

import java.util.List;

public interface BudgetCategoryService {

    BudgetCategoryResponse createBudgetCategory(BudgetCategoryRequest budgetCategoryRequest);
    
    BudgetCategoryResponse updateBudgetCategory(Long budgetCategoryId, BudgetCategoryRequest budgetCategoryRequest);

    BudgetCategoryResponse getBudgetCategory(Long budgetCategoryId);

    List<BudgetCategoryResponse> getBudgetCategories();

    public void deleteBudgetCategory (Long budgetCategoryId);

}
