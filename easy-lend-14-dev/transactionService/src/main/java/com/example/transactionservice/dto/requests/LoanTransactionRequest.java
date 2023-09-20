package com.example.transactionservice.dto.requests;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanTransactionRequest {

    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("borrowerId")

    private String borrowerId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("paymentChoice")
    private String paymentChoice;


    public LoanTransactionRequest(String loanId, String borrowerId, BigDecimal amount, String paymentChoice) {
        this.loanId = loanId;
        this.borrowerId = borrowerId;
        this.amount = amount;
        this.paymentChoice = paymentChoice;
    }


    public LoanTransactionRequest() {
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentChoice() {
        return paymentChoice;
    }

    public void setPaymentChoice(String paymentChoice) {
        this.paymentChoice = paymentChoice;
    }

}
