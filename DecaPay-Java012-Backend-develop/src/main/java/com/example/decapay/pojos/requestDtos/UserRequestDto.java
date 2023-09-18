package com.example.decapay.pojos.requestDtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "firstname required")
    private String firstName;
    @NotBlank(message = "lastname required.")
    private String lastName;
    @NotBlank(message = "email required.")
    private String email;
    @NotBlank(message = "phone number required.")
    private String phoneNumber;
    @NotBlank(message = "password required")
    private String password;
    @NotBlank(message = "please confirm password")
    private String confirmPassword;
}
