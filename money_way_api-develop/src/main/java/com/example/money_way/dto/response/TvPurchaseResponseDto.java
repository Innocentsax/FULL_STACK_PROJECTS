package com.example.money_way.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class TvPurchaseResponseDto {
    private String code;
    private Map<String,?> content;
    private String response_description;
    private String amount;
}
