package com.decagon.loanagreementservice.dtos.request;

public class PaymentChoice {
    private String paymentChoice;

    public PaymentChoice(String paymentChoice) {
        this.paymentChoice = paymentChoice;
    }

    public String getPaymentChoice() {
        return paymentChoice;
    }

    public void setPaymentChoice(String paymentChoice) {
        this.paymentChoice = paymentChoice;
    }
}
