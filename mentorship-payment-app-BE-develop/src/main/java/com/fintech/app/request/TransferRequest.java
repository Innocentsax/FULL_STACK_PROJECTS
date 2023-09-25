package com.fintech.app.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransferRequest {

    @NotNull(message = "account number is required")
    private String accountNumber;
    @NotNull(message = "account name is required")
    private String accountName;
    @NotNull(message = "bank code is required")
    private String bankCode;
    @NotNull(message = "Amount is required")
    private Double amount;
    @NotNull(message = "narration is required")
    private String narration;
    @NotNull(message = "Pin is required")
    private String pin;

}