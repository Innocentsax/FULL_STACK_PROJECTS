package com.decagon.dev.paybuddy.services.paystack;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface PaystackPaymentService {
    ResponseEntity<String> verifyPayment(String reference, String transactionType);
    ResponseEntity<String> paystackPayment(BigDecimal amount, String transactionType);
}
