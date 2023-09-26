package com.example.money_way.repository;

import com.example.money_way.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User>findByConfirmationToken(String confirmationToken);
    Boolean existsByEmail(String email);

}
