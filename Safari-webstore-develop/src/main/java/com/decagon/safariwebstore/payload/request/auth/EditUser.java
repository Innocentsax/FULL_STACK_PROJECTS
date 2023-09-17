package com.decagon.safariwebstore.payload.request.auth;

import com.decagon.safariwebstore.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
