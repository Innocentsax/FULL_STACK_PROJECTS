package com.example.food.services.paystack.payStackPojos;

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
    private String account_number;
    private String bank_code;
    private BigDecimal amount;
    private String email;

}
