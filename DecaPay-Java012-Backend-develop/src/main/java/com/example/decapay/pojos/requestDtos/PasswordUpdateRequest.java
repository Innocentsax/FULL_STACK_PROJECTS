package com.example.decapay.pojos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class PasswordUpdateRequest {

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotBlank(message = "password is mandatory")
    private  String newPassword;

    @NotBlank(message = "password is mandatory")
    private String confirmPassword;


}
