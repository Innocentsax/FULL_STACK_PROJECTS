package com.fintech.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtherBankTransferRequest {

    @JsonProperty("account_bank")
    private String  accountBank;
    @JsonProperty("account_number")
    private String accountNumber;
    private Double amount;
    private String narration;
    private String currency;
    private String reference;

    @JsonProperty("callback_url")
    private String callbackUrl;

    @JsonProperty("debit_currency")
    private String  debitCurrency;

}
