package com.example.money_way.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ElectricityBillRequest {
    private String serviceID;
    private String billersCode;
    private String variationCode;
    private String amount;
    private String phoneNumber;
    private boolean saveBeneficiary;
}
