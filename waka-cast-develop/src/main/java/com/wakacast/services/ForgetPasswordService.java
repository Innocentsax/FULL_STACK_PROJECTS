package com.wakacast.services;

import com.wakacast.dto.ResetPasswordDto;

import java.io.IOException;


public interface ForgetPasswordService {
    boolean generateResetToken(String email) throws IOException;
    void resetPassword(ResetPasswordDto resetPasswordDto, String token);
}
