package com.hrsupportcentresq014.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NominationApprovalRequest {
    @NotBlank(message = "Award field cannot be blank")
    private String awardTitle;

    @Email(message="Invalid email address")
    @NotBlank(message = "Field cannot be blank")
    private String nomineeEmail;
}
