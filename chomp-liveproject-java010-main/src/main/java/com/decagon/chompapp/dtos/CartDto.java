package com.decagon.chompapp.dtos;

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
    private Long userId;

}
