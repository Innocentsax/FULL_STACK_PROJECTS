package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
}
