package com.decagon.OakLandv1be.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderRequestDto {
    private BigDecimal grandTotal;
    private String pickupCenterEmail;
}
