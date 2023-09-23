package com.example.food.repositories;

import com.example.food.model.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    PasswordResetTokenEntity findByToken(String token);
}
