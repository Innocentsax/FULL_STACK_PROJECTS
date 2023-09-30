package com.decagon.dev.paybuddy.services.paystack.payStackPojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class TransferRequest {
    private final String reason ="Withdrawal from pay-buddy Wallet";
    private BigDecimal amount;
    private String reference;
    private String recipient;


}
