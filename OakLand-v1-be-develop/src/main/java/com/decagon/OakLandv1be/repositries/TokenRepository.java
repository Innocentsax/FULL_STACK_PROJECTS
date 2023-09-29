package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
