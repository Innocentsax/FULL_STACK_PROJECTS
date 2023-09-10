package com.goCash.dto.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
@Getter
@Data
public class ChangePasswordRequestDto {
    @NotEmpty(message = "password cannot be empty")
    private String currentPassword;
    @NotEmpty(message = "password cannot be empty")
    private String newPassword;
}
