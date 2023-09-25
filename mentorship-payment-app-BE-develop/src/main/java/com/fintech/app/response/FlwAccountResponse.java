package com.fintech.app.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class FlwAccountResponse {
     private String status;
     private String message;
     private  Data data;

    public FlwAccountResponse() {
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
        return "FlwAccountResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    static class Data {

        @JsonProperty("account_number")
        public String accountNumber;

        @JsonProperty("account_name")
        public String accountName;

        public Data() {
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "accountNumber='" + accountNumber + '\'' +
                    ", accountName='" + accountName + '\'' +
                    '}';
        }
    }
}
