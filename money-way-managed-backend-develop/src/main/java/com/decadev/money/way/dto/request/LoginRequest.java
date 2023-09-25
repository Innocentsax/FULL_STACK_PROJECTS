package com.decadev.money.way.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @Email(message="Invalid email format")
    @NotBlank(message = "Field cannot be empty")
    private String email;

    @NotBlank(message = "Password field cannot be empty")
    @Length(min = 8, max = 24, message = "Password cannot be less than 8 or more than 24 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%&]).*$",
            message = "Password must contain at least one letter, one digit, and one special character")
    private String password;
}
