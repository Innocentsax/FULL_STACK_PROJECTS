package com.example.money_way.service;

import com.example.money_way.dto.request.*;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.UserProfileResponse;
import com.example.money_way.exception.ValidationException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UserService {
    ApiResponse<String> updatePassword(PasswordResetDTO passwordResetDTO);

    ResponseEntity<String> login(LoginRequestDto request);

    ApiResponse verifyLink(VerifyTokenDto verifyTokenDto);

    ResponseEntity<ApiResponse> signUp(SignUpDto signUpDto) throws ValidationException;

    ApiResponse<UserProfileResponse> getUserProfile();

    ApiResponse forgotPassword(ForgotPasswordDTORequest forgotPasswordDTORequest) throws IOException;

    ApiResponse<String> resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO);
}
