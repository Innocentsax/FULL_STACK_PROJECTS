package com.example.food.repositories;

import com.example.food.dto.CartItemDto;
import com.example.food.model.Cart;
import com.example.food.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUsersEmail(String email);

    void deleteById(Long id);

   List<Cart> findAllByUsersOrderById(Users users);
}
