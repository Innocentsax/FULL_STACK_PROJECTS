package com.example.money_way.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataPurchaseResponse {
    private String code;
    private Map<String, ?> content;
    private String response_description;
    private String requestId;
    private String amount;
    private Map<String, ?> transaction_date;
    private String purchased_code;
}