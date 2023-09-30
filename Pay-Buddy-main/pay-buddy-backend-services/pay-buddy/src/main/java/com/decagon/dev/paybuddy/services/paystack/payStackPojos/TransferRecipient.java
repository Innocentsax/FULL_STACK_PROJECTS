package com.decagon.dev.paybuddy.services.paystack.payStackPojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferRecipient {
    private  final String type="nuban";
    private final String currency="NGN";
    private final String description="Initiating withdrawal from customer's wallet";
    private String name;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("bank_code")
    private String bankCode;
    private BigDecimal amount;
    private String email;

}
