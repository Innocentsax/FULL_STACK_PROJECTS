package com.example.food.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AdminPasswordResetRequestDto {
    @NotBlank(message = "Please enter an email")
    private String email;
}
