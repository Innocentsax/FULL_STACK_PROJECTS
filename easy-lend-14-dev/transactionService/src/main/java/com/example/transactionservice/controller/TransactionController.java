package com.example.transactionservice.controller;

import com.example.transactionservice.dto.requests.LoanTransactionRequest;
import com.example.transactionservice.service.serviceImpl.PaymentProcessor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final PaymentProcessor paymentProcessor;


    @PostMapping("/initialize-payment")
    public ResponseEntity<?> initializeTransaction(@RequestBody LoanTransactionRequest request,
                                                   HttpServletRequest servletRequest) {
        return new ResponseEntity<>(paymentProcessor.getProcessor(request.getPaymentChoice().toUpperCase()).makePayment(request, servletRequest), HttpStatus.OK);
    }

}
