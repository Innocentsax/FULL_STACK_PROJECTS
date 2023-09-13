package com.decagon.adire.service;

import com.decagon.adire.dto.request.ForgotPasswordDto;
import com.decagon.adire.dto.request.ResetPasswordRequest;
import com.decagon.adire.dto.response.MessageResponse;

public interface AuthService {
    MessageResponse resetUserSecurityDetails(ResetPasswordRequest resetPasswordRequest);

    String passwordRequest(ForgotPasswordDto forgotPasswordDto);
}
