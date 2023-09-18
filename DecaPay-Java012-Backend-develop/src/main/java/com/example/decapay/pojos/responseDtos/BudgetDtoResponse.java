package com.example.decapay.pojos.responseDtos;

import com.example.decapay.models.Budget;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDtoResponse {

    private Long id;
    private String title;
    private BigDecimal amount;
    private String period;
    private String description;
    public static BudgetDtoResponse convertBudgetToBudgetDtoResponse (Budget budget){
        BudgetDtoResponse response = new BudgetDtoResponse();
        response.setId(budget.getId());
        response.setTitle(budget.getTitle());
        response.setAmount(budget.getAmount());
        response.setPeriod(String.valueOf(budget.getBudgetPeriod()));
        response.setDescription(budget.getDescription());
        return response;
    }
}
