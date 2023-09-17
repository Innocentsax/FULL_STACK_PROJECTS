package com.decagon.safariwebstore.payload.response.auth;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResetPassword {
    private String token;

    @NotNull(message = "password cannot be empty")
    @Size(min = 6, max = 24)
    private String password;

    private String confirmPassword;
}
