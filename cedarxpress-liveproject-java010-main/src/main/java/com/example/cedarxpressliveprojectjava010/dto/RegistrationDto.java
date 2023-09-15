package com.example.cedarxpressliveprojectjava010.dto;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDto {

    @NotBlank(message = "Field cannot be empty")
    private String firstName;

    @NotNull(message = "Field cannot be empty")
    private String lastName;

    @Email(message = "Email is not valid", regexp = "[A-Za-z0-9][A-Za-z0-9!.#$%&'*+=?^_`{|}~-]{1,50}@[A_Za-z]+[.][A-Z.a-z]{2,6}")
    private String email;

    @NotNull(message = "Field cannot be empty")
    private String password;

    @NotNull(message = "Field cannot be empty")
    private String confirmPassword;
}
