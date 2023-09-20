package com.decagon.lendingservice.dto;

import com.decagon.lendingservice.entity.InvestmentPreference;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class InvestmentDTOResponse {
    private String loanId;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private int riskTolerance;
    private int durationInDays;

    public InvestmentDTOResponse(InvestmentPreference savedPreference) {
        this.loanId = savedPreference.getLoanId();
        this.loanAmount = savedPreference.getLoanAmount();
        this.interestRate = savedPreference.getInterestRate();
        this.riskTolerance = savedPreference.getRiskTolerance();
        this.durationInDays = savedPreference.getDurationInDays();
    }


}
