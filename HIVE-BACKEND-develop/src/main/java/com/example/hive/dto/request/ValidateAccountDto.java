package com.example.hive.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class ValidateAccountDto {
    @NotEmpty
    String code;
    @NotEmpty
    String accountNumber;
}
