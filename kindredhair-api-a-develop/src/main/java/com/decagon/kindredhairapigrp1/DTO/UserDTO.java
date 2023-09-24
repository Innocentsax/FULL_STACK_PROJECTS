package com.decagon.kindredhairapigrp1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "Email field cannot be empty.")
    @NotBlank
    @Email
    private String email;
    @NotEmpty(message = "Password field cannot be empty")
    private String password;
    @NotEmpty(message = "Role field cannot be empty")
    private String role;
    @NotEmpty(message = "Status field cannot be empty")
    private String status;
    @NotEmpty(message = "PhoneNumber field cannot be empty")
    private String phoneNumber;

}
