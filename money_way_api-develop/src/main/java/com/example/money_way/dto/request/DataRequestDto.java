package com.example.money_way.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class DataRequestDto {
    private String request_id;
    private String serviceID;
    private String billersCode;
    private String variation_code;
    private BigDecimal amount;
    private String phone;
}
