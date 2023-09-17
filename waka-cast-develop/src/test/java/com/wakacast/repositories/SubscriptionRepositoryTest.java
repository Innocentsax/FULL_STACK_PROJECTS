package com.wakacast.repositories;

import com.wakacast.enums.Gender;
import com.wakacast.models.Subscription;
import com.wakacast.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class SubscriptionRepositoryTest {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionRepositoryTest(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    private Subscription subscription;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("onychris@gmail.com");
        user.setGender(Gender.FEMALE);
        user.setPassword("ONYchris@007");
        subscription = new Subscription();
        subscription.setSubscriber(user);
        subscription.setReference("reference");
        userRepository.save(user);
    }

    @Test
    void saveSubscription() {
        Subscription savedSub = subscriptionRepository.save(subscription);
        assertNotNull(savedSub);
        assertEquals(savedSub, subscription);
    }

    @Test
    void findSubscriptionBySubscriberEmailAndReference() {
        subscriptionRepository.save(subscription);
        Optional<Subscription> returnedSub = subscriptionRepository
                .findSubscriptionBySubscriberEmailAndReference(
                        user.getEmail(), subscription.getReference()
                );
        assertNotNull(returnedSub);
        assertEquals(returnedSub.get(), subscription);
    }
}