package com.decagon.OakLandv1be.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class EditProfileRequestDto {
    @NotNull(message = "Please Provide firstname")
    private String firstName;

    @NotNull(message = "Please Provide lastname")
    private String lastName;

    @Email
    @NotNull(message = "Please select your gender")
    private String email;

    private String date_of_birth;

    private String phone;

    private String address;
}
