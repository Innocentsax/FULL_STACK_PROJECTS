package com.example.money_way.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class TransferToBankResponse {
    private String status;
    private String message;
    private Map<String, ?> data;
}
