package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.CartDto;
import com.decagon.chompapp.dtos.CartItemDto;
import com.decagon.chompapp.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;


    @PostMapping("/add-to-cart/{productId}")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable("productId") long productId){

        return cartService.addToCart(productId);
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<String> clearCart() {
        return cartService.clearCart();
    }
    @PutMapping("/reduce-item-quantity/{cart-item-id}")
    public ResponseEntity<String> reduceItemQuantity(@PathVariable("cart-item-id") long cartItemId) {
        return cartService.reduceQuantityInCart(cartItemId);
    }
    @PutMapping("/increase-item-quantity/{cart-item-id}")
    public ResponseEntity<String> increaseItemQuantity(@PathVariable("cart-item-id") long cartItemId) {
        return cartService.increaseQuantityInCart(cartItemId);
    }
    @DeleteMapping("/remove-cart-item/{cart-item-id}")
    public ResponseEntity<String> removeCartItem(@PathVariable("cart-item-id") long cartItemId) {
        return cartService.removeCartItem(cartItemId);
    }

    @GetMapping("/view-cart")
    public ResponseEntity<CartDto> viewCart(){
        return cartService.viewCart();
    }

}
