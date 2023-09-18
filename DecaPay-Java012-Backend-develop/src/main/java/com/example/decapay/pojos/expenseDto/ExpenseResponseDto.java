package com.example.decapay.pojos.expenseDto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExpenseResponseDto {
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;

}
