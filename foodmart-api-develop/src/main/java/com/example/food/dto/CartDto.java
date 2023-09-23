package com.example.food.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDto {
    private Long cartId;
    private int quantity;
    private double cartTotal;
    private List<CartItemDto> cartItemList;

    public CartDto(List<CartItemDto> cartItems, double cartTotal) {
        this.cartItemList = cartItems;
        this.cartTotal = cartTotal;
    }
}
