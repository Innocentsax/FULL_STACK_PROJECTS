package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.user.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByTokenCode(String tokenCode);
}
