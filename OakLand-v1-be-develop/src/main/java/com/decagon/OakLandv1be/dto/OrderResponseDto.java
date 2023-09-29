package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import lombok.*;
import lombok.Data;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {
    private ModeOfPayment modeOfPayment;
    private Set<OrderItem> items;
    private Double deliveryFee;
    private ModeOfDelivery modeOfDelivery;
    private DeliveryStatus deliveryStatus;
    private Double grandTotal;
    private Double discount;
    private Address address;
    private Transaction transaction;
    private PickupCenter pickupCenter;
}
