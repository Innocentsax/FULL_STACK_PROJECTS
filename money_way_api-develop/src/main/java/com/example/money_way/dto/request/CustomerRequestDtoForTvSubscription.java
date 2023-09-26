package com.example.money_way.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerRequestDtoForTvSubscription {
    private String decoderName;
    private String decoderOrSmartCardNumber;
    private String subscriptionPackage;
    private BigDecimal amount;
    private String pin;
    private String phone;
    private int numberOfMonthlySubscription;
    private String subscriptionType;
    private boolean saveBeneficiary;
}
