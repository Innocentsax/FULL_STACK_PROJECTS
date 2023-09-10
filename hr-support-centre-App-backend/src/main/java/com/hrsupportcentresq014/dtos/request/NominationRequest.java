package com.hrsupportcentresq014.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NominationRequest {

    @NotBlank(message = "Field should not be empty")
    @Email(message = "Input a valid email address")
    private String nomineeEmail;

    @NotBlank(message = "Field should not be empty")
    private String awardTitle;

    @NotBlank(message = "Field should not be empty")
    private String reason;

}
