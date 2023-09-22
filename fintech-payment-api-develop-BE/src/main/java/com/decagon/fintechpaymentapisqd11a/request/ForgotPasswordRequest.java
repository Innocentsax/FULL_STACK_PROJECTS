package com.decagon.fintechpaymentapisqd11a.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {
    @Email
    private String email;
}
