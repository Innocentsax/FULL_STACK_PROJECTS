package com.decagon.borrowerservice.repository;

import com.decagon.borrowerservice.entities.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BorrowerRepository extends JpaRepository<LoanRequest, Long> {
}