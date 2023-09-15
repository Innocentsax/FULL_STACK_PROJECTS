package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionsRepository extends JpaRepository<WalletTransaction, Long> {
}
