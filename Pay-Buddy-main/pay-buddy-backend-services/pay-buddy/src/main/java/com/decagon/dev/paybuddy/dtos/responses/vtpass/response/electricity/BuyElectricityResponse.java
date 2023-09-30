package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyElectricityResponse {
    private String code;
    private Content content;
    private String response_description;
    private String requestId;
    private String amount;
    private TransactionTime transaction_date;
    private String purchased_code;
    private String mainToken;
    private String mainTokenDescription;
    private String mainTokenUnits;
    private String mainTokenTax;
    private String mainsTokenAmount;
    private String bonusToken;
    private String bonusTokenDescription;
    private String bonusTokenUnits;
    private String bonusTokenTax;
    private String bonusTokenAmount;
    private String tariffIndex;
    private String debtDescription;
    private String utilityName;
    private String exchangeReference;
    private String balance;








}
