package com.example.money_way.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountValidationDto {
    private String accountNumber;
    private String accountBank;
}
