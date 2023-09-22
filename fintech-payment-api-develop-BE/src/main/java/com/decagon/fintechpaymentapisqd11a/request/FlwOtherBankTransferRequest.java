package com.decagon.fintechpaymentapisqd11a.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlwOtherBankTransferRequest {
    @JsonProperty("account_bank")
    private String accountBank;

    @JsonProperty("account_number")
    private String accountNumber;
    private Double amount;
    private String narration;
    private String currency;
    private String reference;
    private String callbackUrl;
    private String debitCurrency;

}
