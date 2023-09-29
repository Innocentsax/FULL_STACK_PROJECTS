package com.decagon.OakLandv1be.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ForgotPasswordRequestDto {
    @NotBlank(message = "Email is mandatory")
    private String email;
}
