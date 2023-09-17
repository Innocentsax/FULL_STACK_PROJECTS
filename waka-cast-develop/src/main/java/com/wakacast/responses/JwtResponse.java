package com.wakacast.responses;

import com.wakacast.dto.UserDto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final UserDto userData;
    private final String jwttoken;

    public JwtResponse(UserDto userData, String jwttoken) {
        this.userData = userData;
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public UserDto getUserData() {
        return userData;
    }
}

