package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.OrderResponseDto;
import com.decagon.chompapp.services.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }


    @PostMapping
    public ResponseEntity<OrderResponseDto> checkout(@RequestBody OrderDto orderDto) {
        return checkoutService.checkout(orderDto);
    }
}
