package com.decagon.safariwebstore.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    DELIVERED,
    CANCELLED,
    PENDING,
    REFUNDED
}
