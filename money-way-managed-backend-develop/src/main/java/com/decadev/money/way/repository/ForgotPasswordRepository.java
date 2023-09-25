package com.decadev.money.way.repository;

import com.decadev.money.way.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
    ForgotPassword findPasswordResetRequestByResetToken(String resetToken);
    Optional<ForgotPassword> findByResetToken(String token);
}

