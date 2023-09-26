package com.example.money_way.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TransferFeeResponse {
    private String status;
    private String message;
    private List<Map<String, ?>> data;
}
