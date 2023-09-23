package com.decagon.fitnessoapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String token;
    private String role;
    private PersonInfoResponse userInfo;
}
