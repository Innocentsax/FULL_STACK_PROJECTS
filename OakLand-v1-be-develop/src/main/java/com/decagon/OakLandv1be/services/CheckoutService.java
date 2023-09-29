package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutDto;
import com.decagon.OakLandv1be.dto.CheckoutResponseDto;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import org.springframework.http.ResponseEntity;

public interface CheckoutService {
    ResponseEntity<CheckoutResponseDto> cartCheckout(CheckoutDto checkoutDto);

    ModeOfDelivery modeOfDelivery(String deliveryMethod);

    ResponseEntity<String> addNewAddress(AddressRequestDto request);
}

