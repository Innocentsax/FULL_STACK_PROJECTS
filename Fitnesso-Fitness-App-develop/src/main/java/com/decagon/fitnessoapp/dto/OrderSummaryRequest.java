package com.decagon.fitnessoapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSummaryRequest {

    private BigDecimal subTotal;
    private BigDecimal flatRate;
    private BigDecimal total;
}
