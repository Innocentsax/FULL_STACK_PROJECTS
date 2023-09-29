package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.ForgotPasswordRequestDto;
import com.decagon.OakLandv1be.dto.PasswordResetDto;
import com.decagon.OakLandv1be.dto.UpdatePasswordDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

public interface PersonService {

    String forgotPasswordRequest(ForgotPasswordRequestDto requestDto) throws IOException;
    String resetPassword(String token, PasswordResetDto passwordResetDto);
    ApiResponse<String> updatePassword(UpdatePasswordDto updatePasswordDto);
}
