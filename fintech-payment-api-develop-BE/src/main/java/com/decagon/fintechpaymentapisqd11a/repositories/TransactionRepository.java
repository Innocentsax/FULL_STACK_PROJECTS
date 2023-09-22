package com.decagon.fintechpaymentapisqd11a.repositories;

import com.decagon.fintechpaymentapisqd11a.models.Transaction;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllBySourceAccountNumberOrDestinationAccountNumber
            (String sender, String recipient, Pageable pageable);

    List<Transaction>  findAllByWallet(Wallet wallet);

}
