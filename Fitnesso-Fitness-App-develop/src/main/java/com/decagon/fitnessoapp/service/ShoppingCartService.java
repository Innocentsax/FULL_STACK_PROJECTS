package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.CartResponse;
import com.decagon.fitnessoapp.service.serviceImplementation.PersonDetails;

import java.util.Map;

public interface ShoppingCartService {

     CartResponse addToCart(Map<Long, Integer> products, PersonDetails personDetails);


     CartResponse deleteCart(Long cartId, PersonDetails personDetails);

     void updateTangibleProduct(Long productId, Integer quantity);

     CartResponse getCartById(Long cartId);

     CartResponse viewCartItems();
}
