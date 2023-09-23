package com.example.food.dto;

import lombok.Data;

@Data
public class AdminPasswordResetDto {
    private String token;
    private String password;
}
