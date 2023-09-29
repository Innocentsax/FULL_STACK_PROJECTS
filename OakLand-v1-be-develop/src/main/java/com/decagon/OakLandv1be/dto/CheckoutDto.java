package com.decagon.OakLandv1be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {
    private String modeOfDelivery;
    private Long addressId;
}
