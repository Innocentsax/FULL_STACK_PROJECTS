package com.example.food.repositories;

import com.example.food.model.Order;
import com.example.food.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserOrderByIdDesc(Users users);

    List<Order> findAllByUser_Email(String email);

    Order findOrderByUserAndId(Users users, Long id);
}
