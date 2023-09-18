package com.example.decapay.pojos.requestDtos;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ResetPasswordRequest {
    @Email
    private String email;
    @NotBlank(message = "New password field can't be empty")
    private String newPassword;
    @NotBlank(message = "Confirm password field can't be empty")
    private String confirmPassword;
}
