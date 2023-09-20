package com.easyLend.userservice.services;

import com.easyLend.userservice.domain.entity.VerificationEmail;
import jakarta.servlet.http.HttpServletRequest;

public interface VerificationEmailService {

    String findOtp();
    void saveToken(VerificationEmail confirmationToken);
    String forgotPassword(String token);
    String verifyUser(String token, HttpServletRequest request);
    String sendNewVerificationLink(String email, HttpServletRequest request);
}
