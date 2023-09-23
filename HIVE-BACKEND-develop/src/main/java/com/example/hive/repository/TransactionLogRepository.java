package com.example.hive.repository;

import com.example.hive.constant.TransactionStatus;
import com.example.hive.entity.TransactionLog;
import com.example.hive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, String> {
    List<TransactionLog> findAllByUserAndTransactionStatus(User user, TransactionStatus transactionStatus);
}
