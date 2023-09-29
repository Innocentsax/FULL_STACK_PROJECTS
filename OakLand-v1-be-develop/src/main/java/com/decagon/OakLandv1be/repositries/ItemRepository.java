package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByProductId(Long productId);
}
