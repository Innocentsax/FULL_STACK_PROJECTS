package com.decagon.dev.paybuddy.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class SocialLoginUserRequest {
    @NotBlank(message = "First name cannot be blank")
    private  String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private  String lastName;
    @Email(message = "Email name cannot be blank")
    private  String email;
    private  String profileUrl;
}
