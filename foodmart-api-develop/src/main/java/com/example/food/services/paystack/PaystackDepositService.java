package com.example.food.services.paystack;

import com.example.food.services.paystack.payStackPojos.PaymentDto;
import org.springframework.http.ResponseEntity;

public interface PaystackDepositService {
    ResponseEntity<String> verifyPayment(String reference);
    ResponseEntity<String> fundWallet(PaymentDto paymentDto);
}
