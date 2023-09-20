package com.decagon.loanagreementservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {

    private Long id;
    private String BorrowerId;
    private Long loanId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double interestRate;
    private String repaymentSchedule;
}
