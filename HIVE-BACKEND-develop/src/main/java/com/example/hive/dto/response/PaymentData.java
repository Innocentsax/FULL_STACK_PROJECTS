package com.example.hive.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentData {
    private String status;
    private String reference;
    private double amount;
    private String paidAt;
    private String createdAt;
    private String channel;
    private String currency;
    private String transactionDate;

    }
