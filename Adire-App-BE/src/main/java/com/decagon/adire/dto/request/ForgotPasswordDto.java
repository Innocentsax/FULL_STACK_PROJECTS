package com.decagon.adire.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDto {
    @Email
    private String email;

}
