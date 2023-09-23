package com.example.hive.dto.response;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PayStackTransferResponse {
    BigDecimal amount;
    String account_number;
    String name;
    String bank_code;
    String reference;
    String transfer_date;
    String currency;
    String message;
    String status;
}
