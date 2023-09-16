package com.decagon.chompapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType;

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
