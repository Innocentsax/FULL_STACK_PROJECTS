package com.decagon.eventhubbe.domain.repository;


import com.decagon.eventhubbe.domain.entities.Event;
import com.decagon.eventhubbe.domain.entities.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findAllByEvent(Event event);
}
