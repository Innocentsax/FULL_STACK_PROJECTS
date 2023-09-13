package com.decagon.adire.dto.request;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignerDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private  String lastName;
    @Email
    private String email;

    private String url;
    @NotBlank
    private  String phoneNumber;
    @NotBlank
    private  String password;
    @NotBlank
    private  String confirmPassword;

}
