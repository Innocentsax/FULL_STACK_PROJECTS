package com.decagon.loanagreementservice.loanTransactionService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "LoanTransaction")
public class LoanTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loanId;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private Lender lenderId;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrowerId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Constructors, getters, and setters

    // ...
}