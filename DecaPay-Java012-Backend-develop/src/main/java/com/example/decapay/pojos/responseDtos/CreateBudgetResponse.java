package com.example.decapay.pojos.responseDtos;

import com.example.decapay.models.Budget;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@ToString
public class CreateBudgetResponse {
    private String title;
    private BigDecimal amount;
    private String period;
    private String description;

    public static CreateBudgetResponse convertBudgetToCreateBudgetResponse (Budget budget){
        CreateBudgetResponse response = new CreateBudgetResponse();
        response.setTitle(budget.getTitle());
        response.setDescription(budget.getDescription());
        response.setAmount(budget.getAmount());
        response.setPeriod(String.valueOf(budget.getBudgetPeriod()));
        return response;
    }
}
