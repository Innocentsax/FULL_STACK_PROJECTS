package com.decagon.OakLandv1be.dto.cartDtos;

import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.entities.Cart;
import lombok.Data;

@Data
public class CartItemResponseDto {
    private Long id;
    private String productName;
    private String imageUrl;
    private Integer orderQty;
    private Double unitPrice;
    private Double subTotal;
    private Cart cart;
    private SignupResponseDto customer;
}
