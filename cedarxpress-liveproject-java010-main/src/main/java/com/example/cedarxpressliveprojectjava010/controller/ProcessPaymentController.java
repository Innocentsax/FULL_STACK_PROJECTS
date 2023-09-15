package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.request.ProcessPaymentRequest;
import com.example.cedarxpressliveprojectjava010.service.ProcessPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProcessPaymentController {
    @Autowired
    private ProcessPayment processPayment;

    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(ProcessPaymentRequest processPaymentRequest){
        return processPayment.processPayment(processPaymentRequest);
    }
}
