package com.decagon.adire.dto.request;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @Email
    private  String email;
    @NotBlank
    private String password;
}
