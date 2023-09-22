package com.decagon.fintechpaymentapisqd11a.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExternalBankTransferRequest {
    @NotNull(message = "account number is required")
    private String accountNumber;

    @NotNull(message = "bank code is required")
    private String bankCode;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;


    private String narration;

    @NotNull(message = "Pin is required")
    private String pin;
}