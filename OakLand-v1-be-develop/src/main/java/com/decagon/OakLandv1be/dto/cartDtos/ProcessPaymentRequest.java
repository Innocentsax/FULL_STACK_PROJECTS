package com.decagon.OakLandv1be.dto.cartDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentRequest {
    private BigDecimal grandTotal;

}
