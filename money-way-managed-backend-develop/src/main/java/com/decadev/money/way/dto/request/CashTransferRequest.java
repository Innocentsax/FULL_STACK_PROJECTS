package com.decadev.money.way.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CashTransferRequest {

    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    @Size(min = 10, max = 10, message = "Must be a 10-digit string")
    @NotBlank(message = "Field cannot be empty")
    private String accountNumber;

    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String amount;

    @Size(min = 4, max = 4, message = "Must be a 4-digit string")
    @Pattern(regexp = "\\d+", message = "Only digits are allowed")
    private String pin;

    private String accountBank;

    @Size(max = 100, message = "Description shouldn't be more than 100 characters")
    private String description;

    private boolean saveBeneficiary;
}
