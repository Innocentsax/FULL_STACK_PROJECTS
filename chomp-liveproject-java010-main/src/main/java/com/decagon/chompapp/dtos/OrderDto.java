package com.decagon.chompapp.dtos;

import com.decagon.chompapp.enums.PaymentMethod;
import com.decagon.chompapp.enums.ShippingMethod;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

public class OrderDto {
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentType;
    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;
    private ShippingAddressDto shippingAddress;
}
