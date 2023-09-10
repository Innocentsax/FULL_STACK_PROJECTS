package com.goCash.repository;

import com.goCash.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long > {


}