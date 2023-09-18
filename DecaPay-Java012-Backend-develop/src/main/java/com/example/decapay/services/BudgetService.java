package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.BudgetDto;
import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.BudgetDtoResponse;
import com.example.decapay.pojos.responseDtos.BudgetViewModel;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;

import java.util.List;

public interface BudgetService {

    List<BudgetViewModel> getBudgets(int page, int limit);
    CreateBudgetResponse createBudget(CreateBudgetRequest budgetRequest);

    BudgetViewModel fetchBudgetById(Long budgetId);
    void deleteBudget(Long budgetId);

    BudgetDtoResponse updateBudget(BudgetDto budgetDto, Long budgetId);
}