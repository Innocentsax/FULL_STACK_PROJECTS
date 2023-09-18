package com.example.decapay.pojos.responseDtos;
import com.example.decapay.models.BudgetCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class BudgetCategoryResponse {
    private  Long id;
    private  String name;

    private Boolean isDeleted;

    public static BudgetCategoryResponse mapFrom(BudgetCategory budgetCategory){

        BudgetCategoryResponse budgetCategoryResponse = new BudgetCategoryResponse();
        budgetCategoryResponse.setId(budgetCategory.getId());
        budgetCategoryResponse.setName(budgetCategory.getName());
        budgetCategoryResponse.setIsDeleted(budgetCategory.getIsDeleted());

        return budgetCategoryResponse;
    }

    public static List<BudgetCategoryResponse> mapFromList(List<BudgetCategory> budgetCategoryList){
        return budgetCategoryList.stream()
                .map(BudgetCategoryResponse::mapFrom)
                .collect(Collectors.toList());
    }

}
