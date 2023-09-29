package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.CartDto;
import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;
import java.util.List;

public interface CartService {
    String addItemToCart(Long productId);
    String removeItem(Long itemToRemoveId);
    List<CartItemResponseDto> fetchProductsFromCustomerCart();
    String addToItemQuantity(Long itemId);
    String reduceItemQuantity(Long itemId);
    CartDto viewCartByCustomer ();

    String clearCart();
}



