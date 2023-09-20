package com.decagon.lendingservice.dto;

import com.decagon.lendingservice.entity.InvestmentPreference;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentDTORequest {
    @NotNull(message = "Loan amount must not be null")
    @DecimalMin(value = "0", inclusive = false, message = "Loan amount must be greater than 0")
    private BigDecimal loanAmount;
    @NotNull(message = "Interest rate must not be null")
    private BigDecimal interestRate;
    @NotNull(message = "Duration in days must not be null")
    @Min(value = 1, message = "Duration in days must be greater than or equal to 1")
    private int riskTolerance;
    private int durationInDays;

    public InvestmentDTORequest(InvestmentPreference investmentPreference) {
        this.loanAmount = investmentPreference.getLoanAmount();
        this.interestRate = investmentPreference.getInterestRate();
        this.riskTolerance = investmentPreference.getRiskTolerance();
        this.durationInDays = investmentPreference.getDurationInDays();
    }
}
