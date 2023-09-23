package com.example.food.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordDto {
        private String oldPassword;
        private String newPassword;
        private String confirmPassword;
}
