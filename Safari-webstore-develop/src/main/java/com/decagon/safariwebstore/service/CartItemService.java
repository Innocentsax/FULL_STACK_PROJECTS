package com.decagon.safariwebstore.service;

import com.decagon.safariwebstore.dto.CartItemDTO;
import com.decagon.safariwebstore.model.CartItem;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.payload.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartItemService {
    Response addItemToCart(User user, CartItemDTO cartItemDTO);
    CartItem saveCartItem(CartItem item);
    List<CartItemDTO> getCartItems(User user);
    ResponseEntity<Response> deleteCartItemById(Long id);
}
