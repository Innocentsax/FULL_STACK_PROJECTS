package com.decagon.domain.screen;

import com.decagon.dto.pojoDTO.EmploymentStatusDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmploymentStatus {
    private boolean previouslyEmployed;
    private String employmentSituation;
    private double income;
    private String jobType;

    public EmploymentStatus(EmploymentStatusDTO employmentStatusDTO) {
        this.previouslyEmployed = employmentStatusDTO.isPreviouslyEmployed();
        this.employmentSituation = employmentStatusDTO.getEmploymentSituation();
        this.income = employmentStatusDTO.getIncome();
        this.jobType = employmentStatusDTO.getJobType();
    }
}