package com.example.food.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetRequestDto {

    @NotBlank(message = "email should not be blank")
    private String email;

}
