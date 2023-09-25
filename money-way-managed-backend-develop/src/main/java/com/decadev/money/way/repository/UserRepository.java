package com.decadev.money.way.repository;

import com.decadev.money.way.model.User;
import com.decadev.money.way.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<Object> findByWallet(Wallet wallet);
}
