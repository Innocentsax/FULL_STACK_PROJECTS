package com.example.food.Enum;

import lombok.Getter;
import java.math.BigDecimal;
@Getter
public enum DeliveryMethod {
    FLAT_RATE(new BigDecimal("18.50")),
    EXPEDITED_DELIVERY(new BigDecimal("191.10")),
    OVERNIGHT_DELIVERY(new BigDecimal("345.80"));
    private final BigDecimal fee;
    DeliveryMethod(BigDecimal fee) {
        this.fee = fee;
    }
}
