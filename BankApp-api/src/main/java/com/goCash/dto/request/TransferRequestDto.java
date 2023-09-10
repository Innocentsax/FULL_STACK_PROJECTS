package com.goCash.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDto {
    @JsonProperty("account_bank")
    private String accountBank;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("narration")
    private String narration;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("beneficiary_name")
    private String beneficiaryName;
    @JsonProperty ("debit_currency")
    private String debitCurrency;
}
