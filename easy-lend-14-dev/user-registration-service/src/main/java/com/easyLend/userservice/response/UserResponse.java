package com.easyLend.userservice.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String userId;
    private String fullName;
    private String email;

    public UserResponse(String userId, String fullName, String email) {
        this.userId=userId;
        this.fullName=fullName;
        this.email=email;
    }
}
