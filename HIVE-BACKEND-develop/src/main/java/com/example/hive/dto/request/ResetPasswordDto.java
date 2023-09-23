package com.example.hive.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordDto {
//    private String email;
    private String newPassword;
    private String confirmPassword;
}