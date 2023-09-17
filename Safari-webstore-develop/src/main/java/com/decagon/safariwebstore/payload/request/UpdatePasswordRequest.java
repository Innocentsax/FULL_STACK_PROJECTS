package com.decagon.safariwebstore.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdatePasswordRequest {

    @NotNull(message = "please enter your old password")
    private String oldPassword;
    @NotNull(message = "please enter your new password")
    private String newPassword;
    @NotNull(message = "please confirm your new password")
    private String confirmNewPassword;

}
