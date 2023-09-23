package com.decagon.fitnessoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordRequest {
    private String userName;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}