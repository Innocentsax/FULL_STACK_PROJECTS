package com.fintech.app.response;
import com.fasterxml.jackson.annotation.JsonProperty;
public class FlwVirtualAccountResponse {

    private String status;

    private String message;

    private Data data;

    public FlwVirtualAccountResponse() {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FlwVirtualAccountResponse{" + "status=" + status + ", message=" + message + ", data=" + data + '}';
    }

    public static class Data {

        @JsonProperty(value = "response_code")
        private String responseCode;

        @JsonProperty(value = "response_message")
        private String responseMessage;

        @JsonProperty(value = "flw_ref")
        private String flwRef;

        @JsonProperty(value = "order_ref")
        private String orderRef;

        @JsonProperty(value = "account_number")
        private String accountNumber;

        @JsonProperty(value = "bank_name")
        private String bankName;

        @JsonProperty(value = "note")
        private String note;

        @JsonProperty(value = "frequency")
        private String frequency;

        @JsonProperty(value = "created_at")
        private String createdAt;

        @JsonProperty(value = "amount")
        private String amount;

        public Data() {
        }

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseMessage() {
            return responseMessage;
        }

        public void setResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
        }

        public String getFlwRef() {
            return flwRef;
        }

        public void setFlwRef(String flwRef) {
            this.flwRef = flwRef;
        }

        public String getOrderRef() {
            return orderRef;
        }

        public void setOrderRef(String orderRef) {
            this.orderRef = orderRef;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Data{" + "responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", flwRef=" + flwRef + ", orderRef=" + orderRef + ", accountNumber=" + accountNumber + ", bankName=" + bankName + ", note=" + note + ", frequency=" + frequency + ", createdAt=" + createdAt + ", amount=" + amount + '}';
        }

    }
}