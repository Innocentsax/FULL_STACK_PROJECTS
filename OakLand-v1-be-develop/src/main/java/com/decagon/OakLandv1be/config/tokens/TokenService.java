package com.decagon.OakLandv1be.config.tokens;

import org.springframework.security.core.Authentication;


public interface TokenService {
    String generateToken(Authentication authentication);
    String generatePasswordResetToken(String email);
    String generateVerificationToken(String email);
}
