package com.decagon.safariwebstore.service;

import com.decagon.safariwebstore.dto.OrderRequestDTO;
import com.decagon.safariwebstore.dto.OrderResponseDTO;

public interface CheckOutService {
    OrderResponseDTO doCheckout(OrderRequestDTO orderRequestDTO, String email);
}
