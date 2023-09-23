package com.example.food.pojos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.food.model.Users} entity
 */
@Data
@Builder
public class CreateUserRequest implements Serializable {
    @NotBlank(message = "First name cannot be blank")
    private  String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private  String lastName;
    @Email(message = "Please enter a valid email address")
    private  String email;
    @NotBlank(message = "Password cannot be blank")
    private  String password;
    @NotBlank(message = "Confirm password name cannot be blank")
    private  String confirmPassword;
}
