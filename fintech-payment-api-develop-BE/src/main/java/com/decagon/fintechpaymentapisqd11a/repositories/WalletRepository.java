package com.decagon.fintechpaymentapisqd11a.repositories;

import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WalletRepository extends JpaRepository<Wallet, java.lang.Long> {
    Wallet findWalletById(Long id);

    Wallet findWalletByUsers(Users user);
    Wallet findWalletByAcctNumber(String accountNumber);
    Optional<Wallet> findWalletByAcctNumberAndBankName(final String accountNumber, final String bankName);
    @Query(value = "SELECT * FROM wallet_tbl WHERE user_id = ?", nativeQuery = true)
    Wallet findWalletByUsersId(final Long Id);

}