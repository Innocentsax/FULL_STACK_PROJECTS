package com.decagon.dto.pojoDTO;

import com.decagon.domain.screen.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentStatusDTO {
    private boolean previouslyEmployed;
    private String employmentSituation;
    private double income;
    private String jobType;

    public EmploymentStatusDTO(EmploymentStatus employmentStatus) {
        this.previouslyEmployed = employmentStatus.isPreviouslyEmployed();
        this.employmentSituation = employmentStatus.getEmploymentSituation();
        this.income = employmentStatus.getIncome();
        this.jobType = employmentStatus.getJobType();
    }
}
