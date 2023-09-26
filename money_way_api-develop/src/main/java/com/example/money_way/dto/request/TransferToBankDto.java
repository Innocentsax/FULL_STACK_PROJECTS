package com.example.money_way.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class TransferToBankDto {
    @NotBlank(message = "Bank name cannot be blank")
    private String account_bank;
    @NotBlank(message = "Bank code cannot be blank")
    private String bankCode;
    @NotBlank(message = "Account number cannot be blank")
    private String account_number;
    private String beneficiaryName;
    @NotBlank(message = "Amount cannot be blank")
    private BigDecimal amount;
    @NotBlank(message = "pin cannot be blank")
    private String pin;
    private String description;
    private Boolean saveBeneficiary;
}
