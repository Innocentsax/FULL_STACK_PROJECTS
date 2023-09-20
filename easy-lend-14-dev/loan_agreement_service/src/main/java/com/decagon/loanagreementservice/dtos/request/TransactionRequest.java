package com.decagon.loanagreementservice.dtos.request;

import java.math.BigDecimal;

public class TransactionRequest {
    private BigDecimal amount;
    private String borrowerId;
    private String loanId;
    private String paymentChoice;

    public TransactionRequest(BigDecimal amount, String borrowerId, String loanId, String paymentChoice) {
        this.amount = amount;
        this.borrowerId = borrowerId;
        this.loanId = loanId;
        this.paymentChoice = paymentChoice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getPaymentChoice() {
        return paymentChoice;
    }

    public void setPaymentChoice(String paymentChoice) {
        this.paymentChoice = paymentChoice;
    }
}
