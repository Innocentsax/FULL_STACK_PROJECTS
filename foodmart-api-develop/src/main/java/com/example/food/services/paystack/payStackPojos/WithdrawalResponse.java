package com.example.food.services.paystack.payStackPojos;


import lombok.Setter;

@Setter
@lombok.Data
public class WithdrawalResponse {
    private boolean status;
    private String message;
    private WithdrawalDto data;
}