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

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualAccountCreationRequest {
    @Email(message="Invalid email format")
    @NotBlank(message = "Field cannot be empty")
    private String email;

    private boolean is_permanent;

    @Size(min = 11, max = 11, message = "Must be a 11-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String bvn;

    @Size(min = 11, max = 11, message = "Must be a 11-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String phonenumber;

    @NotBlank(message="Field cannot be empty")
    @Length(min=3, message="Firstname should be more than 2 characters")
    private String firstname;

    @NotBlank(message="Field cannot be empty")
    @Length(min=3, message="Lastname should be more than 2 characters")
    private String lastname;

    @NotBlank(message="Field cannot be empty")
    private String narration;

    private int amount;

}
