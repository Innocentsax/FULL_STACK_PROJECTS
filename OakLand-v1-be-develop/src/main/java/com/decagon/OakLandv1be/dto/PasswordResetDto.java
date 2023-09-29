package com.decagon.OakLandv1be.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetDto {
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;
}
