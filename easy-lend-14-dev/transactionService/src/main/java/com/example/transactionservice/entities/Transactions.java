package com.example.transactionservice.entities;

import com.example.transactionservice.enums.PaymentChoice;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loanId;
    private String borrowId;
    private String lenderId;
    private BigDecimal amount;
    @Temporal(TemporalType.DATE)
    private LocalDate transactionDate;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp()
    private LocalDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String transactionId;
    @Enumerated(EnumType.STRING)
    private PaymentChoice paymentChoice;


    public Transactions(Long id, String loanId, String borrowId, String lenderId, BigDecimal amount, LocalDate transactionDate, LocalDateTime createdAt, LocalDateTime updatedAt, String transactionId, PaymentChoice paymentChoice) {
        this.id = id;
        this.loanId = loanId;
        this.borrowId = borrowId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.transactionId = transactionId;
        this.paymentChoice = paymentChoice;
    }

    public Transactions() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentChoice getPaymentChoice() {
        return paymentChoice;
    }

    public void setPaymentChoice(PaymentChoice paymentChoice) {
        this.paymentChoice = paymentChoice;
    }
}
