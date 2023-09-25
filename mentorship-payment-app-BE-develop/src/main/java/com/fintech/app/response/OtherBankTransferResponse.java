package com.fintech.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Builder
public class OtherBankTransferResponse {
    private String status;
    private String message;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public OtherBankTransferResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OtherBankTransferResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
    @lombok.Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Data {
        private  Long id;
        @JsonProperty("account_number")
        private String  accountNumber;

        @JsonProperty("bank_code")
        private String bankNode;

        @JsonProperty("full_name")
        private String fullName;
        @JsonProperty("created_at")
        private String created_at;

        private String currency;

        @JsonProperty("debit_currency")
        private String debitCurrency;

        private Double  amount;
        private Double fee;
        private String status;
        private String reference;
        private String meta;
        private String narration;
        @JsonProperty("complete_message")
        private String completeMessage;
        @JsonProperty("requires_approval")
        private Integer requires_approval;
        @JsonProperty("is_approved")
        private Integer isApproved;
        @JsonProperty("bank_name")
        private String bankName;
    }
}
