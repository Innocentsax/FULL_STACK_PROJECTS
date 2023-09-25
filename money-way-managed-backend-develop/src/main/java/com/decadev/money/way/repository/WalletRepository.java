package com.decadev.money.way.repository;

import com.decadev.money.way.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByAccountNumber(String accountNumber);
}
