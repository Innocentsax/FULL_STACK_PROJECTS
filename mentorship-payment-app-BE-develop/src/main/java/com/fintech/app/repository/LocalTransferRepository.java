package com.fintech.app.repository;

import com.fintech.app.model.LocalTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LocalTransferRepository extends JpaRepository<LocalTransfer, Long> {

}
