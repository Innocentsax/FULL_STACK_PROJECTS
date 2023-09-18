package com.example.decapay.pojos.responseDtos;

import com.example.decapay.enums.BudgetPeriod;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/**
 * @author Ikechi Ucheagwu
 * @created 19/12/2022 - 00:16
 * @project DecaPay-Java012-Backend
 */

@Data
public class BudgetViewModel {
    private Long budgetId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private BudgetPeriod budgetPeriod;
    private String description;
    private int totalBudgets;
    private BigDecimal amount;
    private BigDecimal totalAmountSpent;
    private BigDecimal percentage;
    private List<LineItemRest> lineItemRests;
}
