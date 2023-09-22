package com.decagon.fintechpaymentapisqd11a.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequest {



    @Size(min = 4, message = "Minimum password length is 4")
    private String newPassword;

    @Size(min = 4, message = "Minimum password length is 4")
    private String confirmPassword;
}
