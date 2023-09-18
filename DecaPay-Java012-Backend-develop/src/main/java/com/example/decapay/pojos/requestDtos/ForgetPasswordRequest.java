package com.example.decapay.pojos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ForgetPasswordRequest {
    @NotBlank(message = "Email field can't be empty")
    private String email;
}
