package com.decagon.dev.paybuddy.repositories;

import com.decagon.dev.paybuddy.models.Transaction;
import com.decagon.dev.paybuddy.models.Wallet;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByWallet(Wallet wallet, Pageable pageable);
}
