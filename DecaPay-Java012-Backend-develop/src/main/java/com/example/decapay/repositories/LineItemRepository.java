package com.example.decapay.repositories;

import com.example.decapay.models.Budget;
import com.example.decapay.models.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
    List<LineItem> findAllByBudget(Budget budget);

}