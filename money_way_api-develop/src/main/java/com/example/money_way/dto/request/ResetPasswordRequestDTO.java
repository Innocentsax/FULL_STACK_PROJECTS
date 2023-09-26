package com.example.money_way.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordRequestDTO {

    @NotNull(message = "New password must not be empty")
    private String newPassword;
    private String token;
}
