package com.decagon.chompapp.services;

import javax.mail.MessagingException;

public interface ForgotPasswordService {
    String generateResetToken(String email) throws MessagingException;
    String resetPassword(String newPassword, String token);
}
