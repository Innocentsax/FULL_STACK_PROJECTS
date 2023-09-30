package com.decagon.dev.paybuddy.dtos.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordRequest {
    @NotBlank(message = "Email field can't be empty")
    private String email;
}
