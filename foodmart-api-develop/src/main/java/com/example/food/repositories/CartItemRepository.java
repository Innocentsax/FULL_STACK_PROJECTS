package com.example.food.repositories;

import com.example.food.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findById(Long cartItemId);

    Optional<CartItem> findByProductId(Long productId);

    Optional<CartItem> findCartItemByCartIdAndProductId(Long cartId, Long productId);
}
