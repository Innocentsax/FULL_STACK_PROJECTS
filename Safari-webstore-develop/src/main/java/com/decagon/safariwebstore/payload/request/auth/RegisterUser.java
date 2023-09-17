package com.decagon.safariwebstore.payload.request.auth;

import com.decagon.safariwebstore.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RegisterUser {
    @NotNull(message = "first name cannot be empty")
    private String firstName;

    @NotNull(message = "last name cannot be empty")
    private String lastName;

    @NotNull(message = "email cannot be empty")
    @Email(message = "must be email")
    private String email;

    private String gender;

    private String dateOfBirth;

    @NotNull(message = "password cannot be empty")
    @Size(min = 6, max = 24)
    private String password;

    @NotNull(message = "password cannot be empty")
    @Size(min = 6, max = 24)
    private String confirmPassword;

    private List<Role> roles;
}
