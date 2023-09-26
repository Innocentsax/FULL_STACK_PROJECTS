package com.example.money_way.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionExpenseDto {
    private BigDecimal total;
}
