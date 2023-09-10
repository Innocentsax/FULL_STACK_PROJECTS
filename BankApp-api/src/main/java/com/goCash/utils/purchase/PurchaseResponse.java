package com.goCash.utils.purchase;

import lombok.Data;

@Data
public class PurchaseResponse {

    private String code;
    private Content content;
    private String response_description;
    private String requestId;
    private String amount;
    private TransactionDate transaction_date;
    private String purchased_code;
    private String mainToken;
    private String mainTokenDescription;
    private double mainTokenUnits;
    private double mainTokenTax;
    private double mainsTokenAmount;
    private String bonusToken;
    private String bonusTokenDescription;
    private double bonusTokenUnits;
    private Double bonusTokenTax;
    private Double bonusTokenAmount;
    private String tariffIndex;
    private String debtDescription;
}
