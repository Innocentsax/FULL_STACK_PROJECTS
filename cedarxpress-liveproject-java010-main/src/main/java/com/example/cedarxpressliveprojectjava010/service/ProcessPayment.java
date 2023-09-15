package com.example.cedarxpressliveprojectjava010.service;


import com.example.cedarxpressliveprojectjava010.dto.request.ProcessPaymentRequest;
import org.springframework.http.ResponseEntity;

public interface ProcessPayment {

    ResponseEntity<String>processPayment(ProcessPaymentRequest processPaymentRequest);
}
