package com.example.food.services.paystack.payStackPojos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private boolean status;
    private String message;
    private PaymentDto data;
}
