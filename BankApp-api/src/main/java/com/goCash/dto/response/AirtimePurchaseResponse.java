package com.goCash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class AirtimePurchaseResponse {

    private String amount;
    private String responseDescription;
    private String transactionType;
    private String Operator;
    private String phone;
    private String transactionId;
    private String transactionDate;
}
