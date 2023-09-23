package com.example.food.dto;


import com.example.food.Enum.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
@Setter
@Getter
public class WalletTransactionDto {
    @NotNull(message = "amount field is empty")
    private BigDecimal amount;
    private Date transactionDate;
    private TransactionType transactionType;
    private String transactionReference;
}
