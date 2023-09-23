package com.decagon.fitnessoapp.dto;

import lombok.Data;

@Data
public class DiscountRequest {
    private String couponCode;

    private float discountPercent;
}
