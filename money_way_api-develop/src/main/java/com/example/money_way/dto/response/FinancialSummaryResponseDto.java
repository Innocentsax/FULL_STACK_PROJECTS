package com.example.money_way.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialSummaryResponseDto {
        private int month;
        private BigDecimal depositsSum;
        private BigDecimal expense;
}

