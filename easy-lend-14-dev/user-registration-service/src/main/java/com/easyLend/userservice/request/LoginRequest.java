package com.easyLend.userservice.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "password cannot be blank")
    private String password;
}
