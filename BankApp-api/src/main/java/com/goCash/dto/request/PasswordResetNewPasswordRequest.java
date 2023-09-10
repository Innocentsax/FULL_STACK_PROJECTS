package com.goCash.dto.request;

import lombok.Data;

@Data
public class PasswordResetNewPasswordRequest {
    private String newPassword;
    private String confirmPassword;
}
