package com.decagon.adire.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    @NotBlank(message = "Please provide the otp sent to your mail")
    private String otp;

    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;

    @NotBlank(message = "Enter new password")
    private String newPassword;

    @NotBlank(message = "Confirm new password")
    private String confirmPassword;

}
