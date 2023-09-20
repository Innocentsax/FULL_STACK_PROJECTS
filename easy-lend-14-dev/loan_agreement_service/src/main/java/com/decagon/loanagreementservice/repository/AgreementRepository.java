package com.decagon.loanagreementservice.repository;

import com.decagon.loanagreementservice.models.LoanAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgreementRepository extends JpaRepository<LoanAgreement, String> {
    Optional<LoanAgreement> findByLoanId(String loanId);
}
