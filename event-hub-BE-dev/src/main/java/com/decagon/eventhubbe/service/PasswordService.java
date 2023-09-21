package com.decagon.eventhubbe.service;

import com.decagon.eventhubbe.dto.request.ResetPasswordRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface PasswordService {
    @Transactional
    String resetPassword(ResetPasswordRequest request);

    String forgotPassword(String email, HttpServletRequest request);
}
