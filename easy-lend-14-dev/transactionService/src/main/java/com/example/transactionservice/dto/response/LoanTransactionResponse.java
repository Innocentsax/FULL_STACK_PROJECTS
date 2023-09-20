package com.example.transactionservice.dto.response;
public class LoanTransactionResponse {
    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static Long serialVersionUID = 2225L;
    private boolean status;
    private String message;
    private Object data;

    public LoanTransactionResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public LoanTransactionResponse() {
    }


    public static void setSerialVersionUID(Long serialVersionUID) {
        LoanTransactionResponse.serialVersionUID = serialVersionUID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
