package com.decagon.chompapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResetPasswordDto {
    private String newPassword;
    private String confirmNewPassword;

}
