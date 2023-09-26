package com.example.money_way.repository;

import com.example.money_way.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    Optional<Beneficiary> findByAccountNumberAndUserId(String accountNumber, Long userId);
    Optional<Beneficiary>findBeneficiaryBySmartCardNumber(String SmartCardNumber);


    Optional<Beneficiary> findBeneficiariesByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM beneficiary_tbl AS b WHERE b.user_id = ?1 AND b.transaction_type ILIKE %?2%",
            nativeQuery = true)
    List<Beneficiary> findAllByUserIdAndTransactionType(Long userId, String transactionType);
    Beneficiary findByEmailAndUserId (String email, Long Id);
    Optional<Beneficiary> findByMeterNumber(String meterNumber);

}

