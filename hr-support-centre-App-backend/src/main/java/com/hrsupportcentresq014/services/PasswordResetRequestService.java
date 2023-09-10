package com.hrsupportcentresq014.services;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface PasswordResetRequestService {
    ResponseEntity<String> resetPassword(String email) throws MessagingException;
    ResponseEntity<String> completePasswordReset(String resetToken, String newPassword);
}
