package com.example.decapay.pojos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineItemResponseDto {

    private Long lineItemId;

    private String budgetCategoryName;

    private BigDecimal budgetAmount;

    private BigDecimal projectedAmount;

    private BigDecimal totalAmountSpent;
}