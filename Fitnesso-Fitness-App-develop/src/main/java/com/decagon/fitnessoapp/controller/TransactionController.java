package com.decagon.fitnessoapp.controller;

import com.decagon.fitnessoapp.dto.transactionDto.response.PaymentResponse;
import com.decagon.fitnessoapp.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/success")
    public ResponseEntity<PaymentResponse> checkTransactionStatus(@RequestParam("reference") String reference){
        return ResponseEntity.ok(transactionService.confirmation(reference));
    }

    @GetMapping("/fail")
    public ResponseEntity<PaymentResponse> transactionDecline(){
        return ResponseEntity.ok(PaymentResponse.builder()
                .message("Transaction Cancel").status("Pending").build());
    }
}
