package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    private String status;
    private String product_name;
    private String unique_element;
    private String unit_price;
    private int quantity;
    private String service_verification;
    private String channel;
    private int commission;
    private int total_amount;
    private String discount;
    private String type;
    private String email;
    private String phone;
    private String name;
    private int convinience_fee;
    private BigDecimal amount;
    private String platform;
    private String method;
    private String transactionId;

}
