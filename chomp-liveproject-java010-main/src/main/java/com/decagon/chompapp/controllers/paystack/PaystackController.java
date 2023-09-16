package com.decagon.chompapp.controllers.paystack;

import com.decagon.chompapp.dtos.paystack.InitializeTransactionRequestDto;
import com.decagon.chompapp.dtos.paystack.InitializeTransactionResponseDto;
import com.decagon.chompapp.services.paystack.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payment")
public class PaystackController {

    private final PaymentService paymentService;

    @PostMapping("initialize")
    public ResponseEntity<InitializeTransactionResponseDto>
        initializeTransaction(@RequestBody InitializeTransactionRequestDto transactionRequestDto) {

        InitializeTransactionResponseDto response =
                paymentService.initializeTransaction(transactionRequestDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("verify")
    public ResponseEntity<StringBuilder> verifyTransaction(@RequestParam String reference) throws IOException {
        StringBuilder response = paymentService.verifyTransaction(reference);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
