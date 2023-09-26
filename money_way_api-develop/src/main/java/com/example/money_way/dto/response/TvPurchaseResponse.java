package com.example.money_way.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TvPurchaseResponse {
    private String code;
    private Map<String,?> content;
    private String response_description;
    private String requestId;
    private String amount;
    private Map<String,?>transaction_date;
    private String purchased_code;
}