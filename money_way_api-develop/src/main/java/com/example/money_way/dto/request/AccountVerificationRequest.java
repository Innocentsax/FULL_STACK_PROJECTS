package com.example.money_way.dto.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AccountVerificationRequest {
    private Long billersCode;
    private String serviceID;
    private String type;
}
