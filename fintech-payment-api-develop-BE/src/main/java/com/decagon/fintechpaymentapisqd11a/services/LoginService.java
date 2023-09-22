package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11a.request.ForgotPasswordRequest;
import com.decagon.fintechpaymentapisqd11a.request.PasswordRequest;

public interface LoginService {
    String authenticate(LoginRequestPayload loginDto) throws Exception;
    String generateResetToken(ForgotPasswordRequest forgotPasswordRequest);
    void sendPasswordResetEmail(String name, String email, String link);
    String resetPassword(PasswordRequest passwordRequest, String token);
}
