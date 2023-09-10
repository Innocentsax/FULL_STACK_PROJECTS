package com.goCash.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goCash.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetails {
    @JsonProperty("status")
    private String status;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("unique_element")
    private String uniqueElement;

    @JsonProperty("unit_price")
    private int unitPrice;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("service_verification")
    private String serviceVerification;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("commission")
    private int commission;

    @JsonProperty("total_amount")
    private int totalAmount;

    @JsonProperty("discount")
    private String discount;

    @JsonProperty("Transaction_type")
    private TransactionType transactionType;

    @JsonProperty("convinience_fee")
    private int convinienceFee;

    @JsonProperty("amount")
    private int amount;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("method")
    private String method;

    @JsonProperty("transactionId")
    private String transactionId;
}
