package com.decagon.OakLandv1be.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

@Configuration
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtAuthenticationProvider refreshTokenAuthProvider;


    public String extractUsername(String token) {
        BearerTokenAuthenticationToken authenticationToken = new BearerTokenAuthenticationToken(token);
        Authentication authentication = refreshTokenAuthProvider.authenticate(authenticationToken);

        Jwt jwt = (Jwt) authentication.getCredentials();
        return jwt.getSubject();
    }
}
