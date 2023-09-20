package com.decagon.borrowerservice.dto;

import com.decagon.borrowerservice.entities.LoanRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanRequestDto {
    @JsonProperty (value = "loanAmt")
    private double loanAmt;
    @JsonProperty (value = "interestRate")
    private double interestRate;

    @JsonProperty (value = "repaymentTerm")
    private int repaymentTerm;
    @JsonProperty (value = "totalRepayment")
    private double totalRepayment;
    @JsonProperty (value = "purpose")
    private String purpose;
    @JsonProperty (value = "createdAt")
    private LocalDateTime createdAt;
    @JsonProperty (value = "updatedAt")
    private LocalDateTime updatedAt;

//    @JsonProperty (value = "requiredDocuments")
//    private String requiredDocuments;

    public LoanRequestDto(LoanRequest loanRequest) {
        this.loanAmt = loanRequest.getLoanAmt();
        this.interestRate = loanRequest.getInterestRate();
        this.repaymentTerm = loanRequest.getRepaymentTerm();
        this.totalRepayment = loanRequest.getTotalRepayment();
        this.purpose = loanRequest.getPurpose();
        this.createdAt = loanRequest.getCreatedAt();
        this.updatedAt = loanRequest.getUpdatedAt();
//        this.requiredDocuments = loanRequest.getRequiredDocuments();
    }
}
