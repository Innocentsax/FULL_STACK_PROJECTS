package com.goCash.dto.response;

import com.goCash.utils.purchase.TransactionDate;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectricityBillResponse {
    private String amount;
    private String responseDescription;
    private String type;
    private String phone;
    private String transactionId;
    private String requestId;
    private String transaction_date;
    private String purchased_code;
    private String mainToken;
    private String mainTokenDescription;
    private double mainTokenUnits;
    private double mainTokenTax;
    private double mainTokenAmount;
    private String bonusToken;
    private String bonusTokenDescription;
    private double bonusTokenUnits;
    private Double bonusTokenTax;
    private Double bonusTokenAmount;
    private String tariffIndex;
    private String debtDescription;
}