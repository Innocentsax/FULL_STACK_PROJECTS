package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseDto {

    private Set<Item> items;

    private Double deliveryFee;

    private ModeOfDelivery modeOfDelivery;

    private DeliveryStatus deliveryStatus;

    private Double grandTotal;

    private Double discount;

    private Address address;

    private Customer customer;

}
