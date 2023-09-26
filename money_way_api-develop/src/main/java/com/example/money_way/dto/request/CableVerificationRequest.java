package com.example.money_way.dto.request;

import lombok.Data;

@Data
public class CableVerificationRequest {
    private Long billersCode;
    private String serviceID;
}
