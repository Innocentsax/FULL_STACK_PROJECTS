package com.decadev.money.way.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AirtimeRequest {
    public String request_id;
    private String serviceID;
    private BigDecimal phone;
    private String phone_x;
    private int amount;
}
