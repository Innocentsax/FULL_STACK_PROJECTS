package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.Amount;
import com.example.cedarxpressliveprojectjava010.dto.InitializeTransactionResponseDto;
import com.example.cedarxpressliveprojectjava010.service.InitializeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/payments")
public class paystackController {
    @Autowired
    private InitializeTransactionService initializeTransactionService;

    @PostMapping("/pay")
    public InitializeTransactionResponseDto initializeTransactionResponseDto
            (@RequestBody Amount amount){

        return initializeTransactionService
                .initializeTransactionResponseDto(amount);
    }


    @GetMapping("/confirm/{reference}")
    public ResponseEntity<String> confirmPayment(@PathVariable String reference) throws IOException {
        return initializeTransactionService.verifyPayment(reference);
    }


}