package com.example.food.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithDrawalDto {
    private String account_number;
    private String bank_code;
    private BigDecimal amount;
}
