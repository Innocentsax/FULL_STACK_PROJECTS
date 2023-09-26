package com.example.money_way.dto.request;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpDto {

    @NotBlank(message = "firstname cannot be blank")
    private String firstName;

    @NotBlank(message = "lastname cannot be blank")
    private String lastName;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "bvn cannot be blank")
    private String bvn;

    @Size(min = 4, max = 4)
    @NotBlank(message = "Pin cannot be blank")
    private String pin;

}
