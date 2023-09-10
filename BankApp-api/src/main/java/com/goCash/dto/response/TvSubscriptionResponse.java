package com.goCash.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
@Data
@Builder

public class TvSubscriptionResponse {



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

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("transaction_date")
    private TransactionDate transactionDate;

    @JsonProperty("purchased_code")
    private String purchasedCode;

    @Data
    @Builder
    public static class Content {
        @JsonProperty("transactions")
        private Transactions transactions;
    }

    @Data
    @Builder
    public static class Transactions {
        @JsonProperty("status")
        private String status;

        @JsonProperty("channel")
        private String channel;

        @JsonProperty("transactionId")
        private String transactionId;

        @JsonProperty("method")
        private String method;

        @JsonProperty("platform")
        private String platform;

        @JsonProperty("is_api")
        private int isApi;

        @JsonProperty("discount")
        private Double discount;

        @JsonProperty("customer_id")
        private int customerId;

        @JsonProperty("email")
        private String email;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("type")
        private String type;

        @JsonProperty("convinience_fee")
        private String convenienceFee;

        @JsonProperty("commission")
        private double commission;

        @JsonProperty("amount")
        private String amount;

        @JsonProperty("total_amount")
        private double totalAmount;

        @JsonProperty("quantity")
        private int quantity;

        @JsonProperty("unit_price")
        private String unitPrice;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("created_at")
        private String createdAt;
    }

    @Data
    @Builder
    public static class TransactionDate {
        @JsonProperty("date")
        private String date;

        @JsonProperty("timezone_type")
        private int timezoneType;

        @JsonProperty("timezone")
        private String timezone;
    }
}