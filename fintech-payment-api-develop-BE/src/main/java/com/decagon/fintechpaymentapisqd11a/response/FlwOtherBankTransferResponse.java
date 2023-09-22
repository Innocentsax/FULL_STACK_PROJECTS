package com.decagon.fintechpaymentapisqd11a.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlwOtherBankTransferResponse {
    private String status;
    private String message;
    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Data {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("account_number")
        private String accountNumber;

        @JsonProperty("bank_code")
        private String bankCode;

        @JsonProperty("full_name")
        private String fullName;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("debit_currency")
        private String debitCurrency;

        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("fee")
        private BigDecimal fee;

        @JsonProperty("status")
        private String status;

        @JsonProperty("reference")
        private String reference;

        @JsonProperty("meta")
        private Object meta;

        @JsonProperty("narration")
        private String narration;

        @JsonProperty("complete_message")
        private String completeMessage;

        @JsonProperty("requires_approval")
        private Integer requiresApproval;

        @JsonProperty("is_approved")
        private Integer isApproved;

        @JsonProperty("bank_name")
        private String bankName;

    }
}
