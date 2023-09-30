package com.decagon.dev.paybuddy.dtos.requests;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginUserRequest {

    @Email(message = "Provide an valid email address")
    private String email;

    @NotBlank(message = "Confirm password name cannot be blank")
    private  String password;
}
