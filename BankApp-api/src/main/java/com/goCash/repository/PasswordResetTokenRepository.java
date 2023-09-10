package com.goCash.repository;

import com.goCash.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String passwordResetToken);
}