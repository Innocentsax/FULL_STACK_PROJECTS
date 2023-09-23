package com.decagon.fitnessoapp.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String newPassword;
    private String confirmPassword;
}
