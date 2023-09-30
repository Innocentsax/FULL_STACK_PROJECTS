package com.decagon.dev.paybuddy.repositories;

import com.decagon.dev.paybuddy.models.User;
import com.decagon.dev.paybuddy.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByUser_Email(String email);
    Wallet findByUser(User user);
    Wallet findByUser_UserId(Long walletUserId);
}
