package com.goCash.repository;

import com.goCash.entities.User;
import com.goCash.entities.WalletAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletAccount,Long> {
    Optional<WalletAccount> findByUser(User user);
    Optional<WalletAccount> findByUserEmail(String email);

    Optional<WalletAccount> findWalletAccountByAccountNumber(String accountNumber);

    Boolean existsByAccountNumber(String accountNumber);
}
