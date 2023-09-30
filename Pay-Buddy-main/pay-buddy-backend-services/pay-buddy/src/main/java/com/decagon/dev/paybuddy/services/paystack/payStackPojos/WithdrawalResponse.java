package com.decagon.dev.paybuddy.services.paystack.payStackPojos;


import lombok.Setter;

@Setter
@lombok.Data
public class WithdrawalResponse {
    private boolean status;
    private String message;
    private PaystackWithdrawalDto data;
}