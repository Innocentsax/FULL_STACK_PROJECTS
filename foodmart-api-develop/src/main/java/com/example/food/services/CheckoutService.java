package com.example.food.services;

import com.example.food.dto.CheckoutDto;
import com.example.food.pojos.OrderResponse;

public interface CheckoutService {
    OrderResponse checkout(CheckoutDto checkoutDto);
}
