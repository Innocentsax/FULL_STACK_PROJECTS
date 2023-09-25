package com.fintech.app.repository;

import com.fintech.app.model.User;
import com.fintech.app.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByUser(User user);
    Wallet findWalletByAccountNumber(String accountNumber);
    Wallet findWalletByTxRef(String txRef);


}