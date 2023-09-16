package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Order;
import com.decagon.chompapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUser(Pageable pageable, User user);

    Optional<Order> findOrderByOrderId(long orderId);

}
