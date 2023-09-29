package com.decagon.OakLandv1be.controllers;


import com.decagon.OakLandv1be.dto.TransactionInitResponseDto;
import com.decagon.OakLandv1be.entities.Amount;
import com.decagon.OakLandv1be.services.TransactionInitService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {

    private final TransactionInitService service;

    @PostMapping( value = "/pay")
    public ResponseEntity<String> processPayment(@RequestBody Amount amount) throws Exception {
        JSONObject response = service.initTransaction(amount);

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @GetMapping("/verify/{reference}")
    public ResponseEntity<Object> confirmPayment(@PathVariable String reference){

        TransactionInitResponseDto response = service.verifyPayment(reference);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/finalizeTrans")
    public ResponseEntity<String> finalizePayment(@RequestParam("reference") String reference){
        return new ResponseEntity<>(service.finalizeTransaction(reference), HttpStatus.OK);
    }

}
