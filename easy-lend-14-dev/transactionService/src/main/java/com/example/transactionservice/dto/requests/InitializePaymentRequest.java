package com.example.transactionservice.dto.requests;




public class InitializePaymentRequest {
    private String email;
    private String currency;
    private String amount;
    private String reference;

    public InitializePaymentRequest(String email, String currency, String amount, String reference) {
        this.email = email;
        this.currency = currency;
        this.amount = amount;
        this.reference = reference;
    }

    public InitializePaymentRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
