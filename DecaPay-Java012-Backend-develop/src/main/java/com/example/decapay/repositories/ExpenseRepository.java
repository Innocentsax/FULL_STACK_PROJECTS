package com.example.decapay.repositories;

import com.example.decapay.models.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query(value = "SELECT * FROM expense_tb WHERE line_item_tb_id=?", nativeQuery = true)
    Page<Expense> findAllLineItemById(Long id, PageRequest pageRequest);
}
