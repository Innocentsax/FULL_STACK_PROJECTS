package com.example.food.dto;

import lombok.Data;

@Data
public class PasswordResetDto {

    private String token;

    private String password;
}
