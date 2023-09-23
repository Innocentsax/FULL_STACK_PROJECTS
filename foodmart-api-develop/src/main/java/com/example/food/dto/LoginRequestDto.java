package com.example.food.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

//    @Email(message = "Email must not be null")
    private String email;

//    @NotNull(message = "Password must not be null")
    private String password;

}
