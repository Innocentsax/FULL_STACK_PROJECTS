package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.OrderResponseDto;
import org.springframework.http.ResponseEntity;

public interface CheckoutService {

    ResponseEntity<OrderResponseDto> checkout(OrderDto orderDto);
}
