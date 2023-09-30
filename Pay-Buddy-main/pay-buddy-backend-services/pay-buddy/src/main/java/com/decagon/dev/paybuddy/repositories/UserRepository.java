package com.decagon.dev.paybuddy.repositories;

import com.decagon.dev.paybuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
//    Optional<User> findByUsOrEmail(String username, String email);

    Boolean existsByEmail(String email);

    Optional<User> findByConfirmationToken(String token);

}
