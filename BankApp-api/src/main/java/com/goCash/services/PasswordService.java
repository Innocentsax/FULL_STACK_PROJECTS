package com.goCash.services;

import com.goCash.dto.request.PasswordResetEmailRequest;
import com.goCash.dto.request.PasswordResetNewPasswordRequest;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


public interface PasswordService {
    String resetPasswordRequest(PasswordResetEmailRequest emailRequest)
            throws MessagingException, UnsupportedEncodingException;
    String resetPassword(PasswordResetNewPasswordRequest newPasswordRequest, String token);
}

