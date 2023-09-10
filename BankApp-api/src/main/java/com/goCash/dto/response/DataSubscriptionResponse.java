package com.goCash.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataSubscriptionResponse {
    @JsonProperty("content")
    private Content content;

    @JsonProperty("code")
    private String code;

    @JsonProperty("response_description")
    private String responseDescription;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("Operator")
    private String operator;

    @JsonProperty("purchased_code")
    private String purchasedCode;

    @Data
    public static class Content {
        @JsonProperty("transactions")
        private Transactions transactions;
    }

    @Data
    public static class Transactions {
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
        private Object serviceVerification;

        @JsonProperty("channel")
        private String channel;

        @JsonProperty("commission")
        private int commission;

        @JsonProperty("total_amount")
        private int totalAmount;

        @JsonProperty("discount")
        private Object discount;

        @JsonProperty("type")
        private String type;

        @JsonProperty("email")
        private String email;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("name")
        private Object name;

        @JsonProperty("convinience_fee")
        private int convenienceFee;

        @JsonProperty("amount")
        private int transactionAmount;

        @JsonProperty("platform")
        private String platform;

        @JsonProperty("method")
        private String method;

        @JsonProperty("transactionId")
        private String transactionId;
    }

    @Data
    public static class TransactionDate {
        @JsonProperty("date")
        private String date;

        @JsonProperty("timezone_type")
        private int timezoneType;

        @JsonProperty("timezone")
        private String timezone;
    }
}
