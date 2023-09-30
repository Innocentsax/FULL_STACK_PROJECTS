package com.decagon.dev.paybuddy.dtos.requests;

import com.decagon.dev.paybuddy.enums.TransactionStatus;
import com.decagon.dev.paybuddy.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TransactionDto {
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDate dateOfTransaction;
    private TransactionStatus transactionStatus;
    private String description;
}
