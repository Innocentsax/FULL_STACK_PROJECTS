package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.product.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUniqueCartId(String uniqueId);
    List<Cart> findAllByUniqueCartId(String uniqueId);
}
