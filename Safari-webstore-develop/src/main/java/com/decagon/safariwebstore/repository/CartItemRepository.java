package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.CartItem;
import com.decagon.safariwebstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Boolean existsByUserAndProductId(User user, Long productId);
    Optional<CartItem> findByUserAndProductId(User user, Long productId);
    Optional<CartItem> findByUserAndId(User user, Long id);
    List<CartItem> findAllByUser(User user);
}