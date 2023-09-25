package com.decadev.money.way.repository;

import com.decadev.money.way.enums.TransactionType;
import com.decadev.money.way.model.Beneficiary;
import com.decadev.money.way.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    Optional<Beneficiary> findByUserAndTransactionType(User recipient, TransactionType transactionType);
    List<Beneficiary> findAllByUserAndTransactionType(User recipient, TransactionType transactionType);
}
