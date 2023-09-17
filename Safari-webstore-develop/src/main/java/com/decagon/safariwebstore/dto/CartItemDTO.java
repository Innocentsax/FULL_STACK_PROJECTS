package com.decagon.safariwebstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long id;

    private Long productId;

    private String quantity;

    private Double price;

    private String name;

    private String description;

    private String size;

    private String color;

    private String productImage;

}
