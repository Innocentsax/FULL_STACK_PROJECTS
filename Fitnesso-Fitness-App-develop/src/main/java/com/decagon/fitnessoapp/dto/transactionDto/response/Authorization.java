package com.decagon.fitnessoapp.dto.transactionDto.response;

import lombok.Data;

@Data
public class Authorization {
    private String signature;
    private String account_name;
    private boolean reusable;
    private String brand;
    private String country_code;
    private String bank;
    private String card_type;
    private String channel;
    private String exp_year;
    private String exp_month;
    private String last4;
    private String bin;
    private String authorizationCode;
}
