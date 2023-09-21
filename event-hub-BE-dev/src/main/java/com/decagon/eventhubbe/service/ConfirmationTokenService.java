package com.decagon.eventhubbe.service;

import com.decagon.eventhubbe.domain.entities.ConfirmationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);

    String verifyUser(String token, HttpServletRequest request);

    String sendNewVerificationLink(String email, HttpServletRequest request);

    String forgotPassword(String token);
}
