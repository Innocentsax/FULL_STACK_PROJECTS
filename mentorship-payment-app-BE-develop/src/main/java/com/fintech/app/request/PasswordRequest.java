package com.fintech.app.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordRequest {

    @Email
    private String email;

    @Size(min = 4, max = 4, message = "Pin must be of 4 characters")
    private String oldPassword;

    @Size(min = 4, message = "Minimum password length is 4")
    private String newPassword;

    @Size(min = 4, message = "Minimum password length is 4")
    private String confirmPassword;
}

