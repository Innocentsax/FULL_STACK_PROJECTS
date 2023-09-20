package com.decagon.loanagreementservice.dtos.response;
import com.decagon.loanagreementservice.models.LoanAgreement;
import com.decagon.loanagreementservice.models.Status;
import java.math.BigDecimal;



public class LoanAgreementDto {
    private String id;

    private String loanId;

    private String borrowerId;


    private String lenderId;

    private BigDecimal loanAmount;


    private BigDecimal interestRate;


    private int durationInDays;

    private int riskTolerance;

    private Status status;


    private String conditions;

    public LoanAgreementDto(LoanAgreement loanAgreement) {
        this.id = loanAgreement.getLoanAgreementId();
        this.loanId = loanAgreement.getLoanId();
        this.borrowerId = loanAgreement.getBorrowerId();
        this.lenderId = loanAgreement.getLenderId();
        this.loanAmount = loanAgreement.getLoanAmount();
        this.interestRate = loanAgreement.getInterestRate();
        this.durationInDays = loanAgreement.getDurationInDays();
        this.status = loanAgreement.getStatus();
        this.conditions = loanAgreement.getConditions();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public int getRiskTolerance() {
        return riskTolerance;
    }

    public void setRiskTolerance(int riskTolerance) {
        this.riskTolerance = riskTolerance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}