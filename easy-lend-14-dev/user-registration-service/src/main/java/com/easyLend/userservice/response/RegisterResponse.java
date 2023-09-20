package com.easyLend.userservice.response;

import com.easyLend.userservice.domain.constant.UserType;
import lombok.Data;

@Data
public class RegisterResponse {
    private String fullName;
    private String email;
    private String image;
    private UserType userType;
    private Boolean registrationStatus;


}
