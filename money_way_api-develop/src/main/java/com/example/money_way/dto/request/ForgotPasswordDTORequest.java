package com.example.money_way.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ForgotPasswordDTORequest {
    @NotNull(message = "Email must not be empty")
    private String email;

}
