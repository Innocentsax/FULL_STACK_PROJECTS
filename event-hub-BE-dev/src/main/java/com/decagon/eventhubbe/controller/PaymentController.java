package com.decagon.eventhubbe.controller;

import com.decagon.eventhubbe.dto.request.PaymentRequest;
import com.decagon.eventhubbe.dto.response.APIResponse;
import com.decagon.eventhubbe.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/event/{eventId}/ticket")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<String>> makePayment(@RequestBody PaymentRequest paymentRequest, @PathVariable String eventId){
        APIResponse<String> apiResponse = new APIResponse<>(paymentService.makePayment(paymentRequest,eventId));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
