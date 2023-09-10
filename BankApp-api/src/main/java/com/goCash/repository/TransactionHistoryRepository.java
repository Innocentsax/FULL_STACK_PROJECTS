package com.goCash.repository;

import com.goCash.entities.Transaction;
import com.goCash.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findTransactionsByUser(User user, Pageable pageable);
}
