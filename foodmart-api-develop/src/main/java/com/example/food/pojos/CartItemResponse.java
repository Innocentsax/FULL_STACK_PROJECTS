package com.example.food.pojos;

import com.example.food.model.Cart;
import com.example.food.model.Product;
import com.example.food.restartifacts.BaseResponse;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CartItemResponse extends BaseResponse {
    private Product product;
    private int quantity;
    private BigDecimal subTotal = product.getProductPrice().multiply(BigDecimal.valueOf(quantity));
}
