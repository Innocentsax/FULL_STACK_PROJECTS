package com.example.money_way.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ElectricityRequestDto {
    private String request_id;
    private String serviceID;
    private String billersCode;
    private String variation_code;
    private String amount;
    private String phone;
}
