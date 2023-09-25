package com.fintech.app.service;

import com.fintech.app.request.LoginRequest;
import com.fintech.app.request.PasswordRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.JwtAuthResponse;

import javax.mail.MessagingException;

public interface LoginService {
    BaseResponse<JwtAuthResponse> login(LoginRequest loginRequest) throws Exception;
    BaseResponse<?> logout();

    BaseResponse<String> changePassword(PasswordRequest passwordRequest);

    BaseResponse<String> generateResetToken(PasswordRequest passwordRequest) throws MessagingException;
    BaseResponse<String> resetPassword(PasswordRequest passwordRequest, String token);

}
