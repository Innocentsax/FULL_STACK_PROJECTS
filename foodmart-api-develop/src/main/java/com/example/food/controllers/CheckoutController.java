package com.example.food.controllers;

import com.example.food.dto.CheckoutDto;
import com.example.food.pojos.OrderResponse;
import com.example.food.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping()
    public ResponseEntity<OrderResponse> checkout(@RequestBody CheckoutDto checkoutDto) {
        OrderResponse response = checkoutService.checkout(checkoutDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}