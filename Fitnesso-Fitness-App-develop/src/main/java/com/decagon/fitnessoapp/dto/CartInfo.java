package com.decagon.fitnessoapp.dto;

import com.decagon.fitnessoapp.model.product.Product;
import com.decagon.fitnessoapp.model.product.TangibleProduct;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
public class CartInfo {
    private Long id;

    private String uniqueCartId;

    private Product product;

    private Integer quantity;

    private Long personId;

    private String cartReference;
}
