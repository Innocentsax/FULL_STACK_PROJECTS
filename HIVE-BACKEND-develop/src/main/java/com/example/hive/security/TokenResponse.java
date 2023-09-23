package com.example.hive.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenResponse {

    private String token;
    private String refreshToken;
    private String issuedDate;
    private String expirationDate;
}
