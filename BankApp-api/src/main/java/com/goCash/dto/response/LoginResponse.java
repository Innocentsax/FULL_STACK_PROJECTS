package com.goCash.dto.response;

import com.goCash.enums.Roles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String firstName;
    private String lastName;
    private String token;
    private String email;
    private Roles role;

}

