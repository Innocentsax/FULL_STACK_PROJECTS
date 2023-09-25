package com.decadev.money.way.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @Email(message="Invalid email format")
    @NotBlank(message = "Field cannot be empty")
    private String email;

    @NotBlank(message="Field cannot be empty")
    @Length(min=3, message="Lastname should be more than 2 characters")
    private String firstName;

    @NotBlank(message="Field cannot be empty")
    @Length(min=3, message="Lastname should be more than 2 characters")
    private String lastName;

    @Size(min = 11, max = 11, message = "Must be a 11-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String phoneNumber;

    @NotBlank(message = "Password field cannot be empty")
    @Length(min = 8, max = 24, message = "Password cannot be less than 8 or more than 24 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%&]).*$",
            message = "Password must contain at least one letter, one digit, and one special character")
    private String password;

    @Size(min = 4, max = 4, message = "Must be a 4-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String pin;

    @Size(min = 11, max = 11, message = "Must be a 11-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String bvn;
}
