package com.decagon.dev.paybuddy.services.paystack.payStackPojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaystackWithdrawalDto {
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("bank_id")
    private int bankId;
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("recipient_code")
    private String recipientCode;
    private String reference;
}
