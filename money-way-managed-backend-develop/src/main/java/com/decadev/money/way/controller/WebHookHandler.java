package com.decadev.money.way.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/money-way/webhook")
@CrossOrigin("*")
@RequiredArgsConstructor
public class WebHookHandler {

    @PostMapping("/bank-transfer")
    public ResponseEntity<?> bankTransferConfirmation(@RequestBody String payLoad){
            return null;
    }
}
