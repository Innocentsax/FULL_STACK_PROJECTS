package com.decadev.money.way.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTransactionPinRequest {


    @Size(min = 4, max = 4, message = "Must be a 4-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String oldPin;

    @Size(min = 4, max = 4, message = "Must be a 4-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String newPin;

    @Size(min = 4, max = 4, message = "Must be a 4-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String confirmNewPin;

}
