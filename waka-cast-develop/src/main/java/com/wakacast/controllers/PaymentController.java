package com.wakacast.controllers;

import com.wakacast.dto.SubscriptionDTO;
import com.wakacast.dto.payment_dtos.initial_transaction.InitializeTransactionResponseDTO;
import com.wakacast.responses.ResponseData;
import com.wakacast.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.wakacast.controllers.UserController.response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/subscribe")
    public ResponseEntity<InitializeTransactionResponseDTO> initializeTransaction(
            @RequestBody SubscriptionDTO subscriptionDTO, HttpServletRequest request) {
        return new ResponseEntity<>(paymentService.initializeTransaction(subscriptionDTO, request), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/confirm/{token}")
    public ResponseEntity<ResponseData> confirmPayment(
            @PathVariable String token, @RequestParam String reference,
            @RequestParam String trxref
    ) throws IOException {
        return response(HttpStatus.OK, paymentService.confirmPayment(token, reference));
    }
}
