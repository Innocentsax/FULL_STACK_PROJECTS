package com.goCash.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Data
public class LoginRequest {
    @NotEmpty(message = "password cannot be empty")
    private String password;
    @NotEmpty(message = "please email cannot be empty")
    @Email
    private String email;
}
