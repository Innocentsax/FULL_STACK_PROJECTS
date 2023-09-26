package com.example.money_way.dto.request;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class VtPassTvPurchaseRequest {
    private String request_id;
    private String serviceID;
    private String billersCode;
    private String variation_code;
    private String phone;
    private String subscription_type;
    private int quantity;
}