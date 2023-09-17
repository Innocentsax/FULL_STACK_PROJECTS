package com.decagon.safariwebstore.payload.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private List<String> roles;

    public LoginResponse(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }
}
