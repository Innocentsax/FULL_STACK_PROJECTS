package com.decagon.domain.screen;

import com.decagon.dto.pojoDTO.IncomeStatusDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncomeStatus {
    private String employmentStatus;
    private String monthlyPersonalIncome;
    private boolean hasOtherSourcesOfIncome;
    private String extraIncomeDescription;

    public IncomeStatus(IncomeStatusDTO incomeStatusDTO) {
        this.employmentStatus = incomeStatusDTO.getEmploymentStatus();
        this.monthlyPersonalIncome = incomeStatusDTO.getMonthlyPersonalIncome();
        this.hasOtherSourcesOfIncome = incomeStatusDTO.isHasOtherSourcesOfIncome();
        this.extraIncomeDescription = incomeStatusDTO.getExtraIncomeDescription();
    }
}
