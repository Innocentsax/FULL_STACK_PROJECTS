package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.CartDto;
import com.decagon.chompapp.dtos.CartItemDto;
import org.springframework.http.ResponseEntity;
import javax.transaction.Transactional;
import com.decagon.chompapp.models.CartItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
    ResponseEntity<CartItemDto> addToCart(long productId);

    ResponseEntity<String> clearCart();

    ResponseEntity<String> removeCartItem(long cartItemId);

    ResponseEntity<String> reduceQuantityInCart(long cartItemId);

    ResponseEntity<String> increaseQuantityInCart(long cartItemId);

    ResponseEntity<CartDto> viewCart();
}
