package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.Order;
import com.decagon.safariwebstore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByStatusAndUser(String status, User user, Pageable pageable);
    Page<Order> findByStatus(String status, Pageable pageable);
    Page<Order> findByUser(User user, Pageable pageable);
}
