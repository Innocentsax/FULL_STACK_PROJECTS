package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.user.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
}
