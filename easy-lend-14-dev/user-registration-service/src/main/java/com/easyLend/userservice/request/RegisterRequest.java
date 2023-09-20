package com.easyLend.userservice.request;

import com.easyLend.userservice.domain.constant.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class RegisterRequest {
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "FullName cannot be blank")
    private String fullName;
    private UserType userType;
    @NotBlank(message = "password cannot be blank")
    private String password;


}
