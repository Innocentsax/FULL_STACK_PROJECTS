package com.decagon.adire.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDesigner {
    @NotBlank
    private String firstName;
    @NotBlank
    private  String lastName;
    @Email
    private String email;

    private String url;
    @NotBlank
    private  String phoneNumber;
}
