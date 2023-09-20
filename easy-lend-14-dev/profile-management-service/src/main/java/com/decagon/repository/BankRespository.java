package com.decagon.repository;

import com.decagon.domain.entity.Banks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRespository extends JpaRepository<Banks,Long> {
    Optional<Banks> findBanksByBankName(String bankName);
}
