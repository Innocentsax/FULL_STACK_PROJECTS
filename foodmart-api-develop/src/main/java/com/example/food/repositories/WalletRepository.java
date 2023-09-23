package com.example.food.repositories;

import com.example.food.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByUser_Email(String email);
}
