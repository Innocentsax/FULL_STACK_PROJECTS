package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
