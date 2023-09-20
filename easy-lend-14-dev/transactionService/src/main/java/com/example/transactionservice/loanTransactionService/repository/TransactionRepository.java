package com.decagon.loanAgreementSelection.loanTransactionService.repository;

import com.decagon.loanAgreementSelection.loanTransactionService.model.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<LoanTransaction, Long> {
}
