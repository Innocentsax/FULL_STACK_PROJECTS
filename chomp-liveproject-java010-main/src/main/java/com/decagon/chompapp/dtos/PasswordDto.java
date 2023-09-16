package com.decagon.chompapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
