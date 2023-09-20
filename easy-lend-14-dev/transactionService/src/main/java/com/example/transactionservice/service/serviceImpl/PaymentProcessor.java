package com.example.transactionservice.service.serviceImpl;

import com.example.transactionservice.enums.PaymentChoice;
import com.example.transactionservice.repositories.TransactionRepository;
import com.example.transactionservice.securityConfig.JWTUtils;
import com.example.transactionservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PaymentProcessor {
    private final TransactionRepository transactionRepository;
    private final JWTUtils jwtUtils;

    public PaymentService getProcessor(String paymentChoice){
        switch (paymentChoice){
            case "PAYSTACK" -> {
                return new PayStackPaymentServiceImpl(transactionRepository, jwtUtils);
            }
            case "MOCKED" -> {
                return new MockedPaymentServiceImpl(transactionRepository, jwtUtils);
            }
            case "FLUTTERWAVE" -> {
                return new MockedPaymentServiceImpl(transactionRepository, jwtUtils);
            }
            default -> {
                return new PayStackPaymentServiceImpl(transactionRepository, jwtUtils);
            }
        }
    }
}
