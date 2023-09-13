package com.decagon.adire.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
    @NotBlank
    private String token;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
