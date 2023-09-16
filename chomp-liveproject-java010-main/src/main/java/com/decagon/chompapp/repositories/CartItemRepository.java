package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Cart;
import com.decagon.chompapp.models.CartItem;
import com.decagon.chompapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    void deleteAllByCart_CartId(long id);
    Optional<CartItem> findByCart_CartIdAndCartItemId(long cartId, long cartItemId);
    List<CartItem> findCartItemByCart_CartId(long cartId);
}
