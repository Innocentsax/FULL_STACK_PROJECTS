package com.decagon.loanagreementservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanOffer {
    private String id;
    private String lenderId;
    private String loanId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal interestRate;
    private int durationInDays;

    // Constructors, getters, and setters
}