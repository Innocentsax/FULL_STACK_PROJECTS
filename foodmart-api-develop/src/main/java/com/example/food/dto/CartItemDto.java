package com.example.food.dto;

import com.example.food.model.Product;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private Long id;
    private Product product;
    private int quantity;
    private BigDecimal subTotal;

}
