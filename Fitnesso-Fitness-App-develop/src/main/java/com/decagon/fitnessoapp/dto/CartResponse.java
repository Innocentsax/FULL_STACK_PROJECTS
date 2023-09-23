package com.decagon.fitnessoapp.dto;

import com.decagon.fitnessoapp.model.product.Cart;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {
    private List<CartInfo> cartData;
    private List<Cart> cartList;
    private Cart cart;
    private String message;
}
