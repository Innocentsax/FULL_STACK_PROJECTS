package com.decagon.dev.paybuddy.dtos.requests;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotBlank(message = "New password field can't be empty")
    private String newPassword;
    @NotBlank(message = "Confirm password field can't be empty")
    private String confirmPassword;
}
