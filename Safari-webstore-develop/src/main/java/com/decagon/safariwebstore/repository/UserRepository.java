package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByPasswordResetToken(String token);

    List<User> findAllByPasswordResetTokenIsNotNull();
}
