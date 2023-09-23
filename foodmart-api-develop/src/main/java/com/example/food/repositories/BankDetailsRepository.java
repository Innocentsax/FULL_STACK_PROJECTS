package com.example.food.repositories;

import com.example.food.model.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
    BankDetails findByAccountNumberAndBankCode(String accountNumber, String bankCode);
}
