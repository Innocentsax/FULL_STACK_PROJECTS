package com.decadev.money.way.service;

import com.decadev.money.way.dto.request.ResetPassword;
import org.springframework.http.ResponseEntity;

public interface ForgotPasswordService {
    ResponseEntity<String> resetForgotPassword(String email) ;

    ResponseEntity<String> ResetPassword(String token,ResetPassword resetPassword);
    ResponseEntity<Boolean> validToken(String token);
}