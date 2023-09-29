package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.PaystackTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaystackTransactionRepository extends JpaRepository<PaystackTransaction, Long> {
    Optional<PaystackTransaction> findByReference(String reference);
}
