package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.dto.CartDto;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.services.CartService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/item/add/{productId}")
    public ResponseEntity<String> addItemToCart(@PathVariable Long productId) throws AlreadyExistsException {
        String response = cartService.addItemToCart(productId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId){
       cartService.removeItem(itemId);
        return new ResponseEntity<>( "Item removed successfully", HttpStatus.OK);
    }

    @PutMapping("/item/add-to-quantity/{productId}")
    public ResponseEntity<String> addToItemQuantity(@PathVariable Long productId){
        return new ResponseEntity<>(cartService.addToItemQuantity(productId), HttpStatus.OK);
    }

    @PutMapping("/item/reduce-quantity/{productId}")
    public ResponseEntity<String> reduceItemQuantity(@PathVariable Long productId){
        return new ResponseEntity<>(cartService.reduceItemQuantity(productId), HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(){
        return new ResponseEntity<>(cartService.clearCart(), HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<CartDto> viewCartByCustomer (){
        return new ResponseEntity<>(cartService.viewCartByCustomer(), HttpStatus.OK);
    }
}
