package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Transaction;
import com.decagon.OakLandv1be.entities.Wallet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByWallet(Wallet wallet, PageRequest of);

}
