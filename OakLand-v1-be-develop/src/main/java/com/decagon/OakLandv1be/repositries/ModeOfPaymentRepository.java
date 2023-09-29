package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.ModeOfPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeOfPaymentRepository extends JpaRepository<ModeOfPayment, Long> {

    Optional<ModeOfPayment> findByName(String name);
}