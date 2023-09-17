package com.wakacast.repositories;

import com.wakacast.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findSubscriptionBySubscriberEmailAndReference(String email, String reference);
}
