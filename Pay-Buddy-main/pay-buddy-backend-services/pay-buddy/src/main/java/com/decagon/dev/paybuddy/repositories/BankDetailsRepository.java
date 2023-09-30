package com.decagon.dev.paybuddy.repositories;

import com.decagon.dev.paybuddy.models.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
    BankDetails findByAccountNumberAndBankCode(String accountNumber, String bankCode);
}
