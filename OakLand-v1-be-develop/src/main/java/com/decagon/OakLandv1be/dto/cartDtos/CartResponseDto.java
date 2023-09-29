package com.decagon.OakLandv1be.dto.cartDtos;

import lombok.Data;

@Data
public class CartResponseDto {
    private String productName;
    private String imageUrl;
    private Integer orderQty;
    private Double unitPrice;
    private Double subTotal;
}
