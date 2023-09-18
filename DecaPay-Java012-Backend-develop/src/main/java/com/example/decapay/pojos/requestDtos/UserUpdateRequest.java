package com.example.decapay.pojos.requestDtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter

public class UserUpdateRequest {
    @NotBlank(message = "First Name field can't be empty")
    private String firstName;
    @NotBlank(message = "Last Name field can't be empty")
    private String lastName;
    @NotBlank(message="Phone Number field can't be empty")
    private String phoneNumber;

}
