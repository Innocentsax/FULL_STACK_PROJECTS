package com.decagon.adire.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;

//    @NotBlank
//    @Size( max = 4)
//    private String otp;
}
