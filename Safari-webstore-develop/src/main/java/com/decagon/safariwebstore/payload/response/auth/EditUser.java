package com.decagon.safariwebstore.payload.response.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class EditUser {
    @NotNull(message = "first name cannot be empty")
    private String firstName;

    @NotNull(message = "last name cannot be empty")
    private String lastName;

    @NotNull(message = "email cannot be empty")
    @Email(message = "must be email")
    private String email;

    private String gender;

    private String dateOfBirth;

}
