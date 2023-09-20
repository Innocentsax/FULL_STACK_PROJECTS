package com.decagon.lendingservice.repo;

import com.decagon.lendingservice.entity.InvestmentPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import java.util.Optional;
import java.util.UUID;

public interface  InvestmentPreferenceRepository extends JpaRepository<InvestmentPreference, Long>{
//@Query(value = "select * from investment_preference where user_id = ?1", nativeQuery = true)
    InvestmentPreference findByUserId(String userId);
    @Query(value = "select * from investment_preference where loan_id = ?1", nativeQuery = true)
    Optional<InvestmentPreference> findInvestmentPreferenceById (String loanId);



}
