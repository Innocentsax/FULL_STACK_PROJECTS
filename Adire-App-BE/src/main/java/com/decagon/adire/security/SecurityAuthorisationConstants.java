package com.decagon.adire.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

//@NoArgsConstructor(access= AccessLevel.PRIVATE)
@Getter
@Component
public class SecurityAuthorisationConstants {

    private final String[] PUBLIC_URIS = new String[]{
            "/",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/#/**",
            "/actuator/**",
            "/instances/**",
            "/api/v1/auth/**",
            "/api/test",
            "/",
            "/error",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/api/auth/forgot-password-request",
            "/api/auth/reset-password"
    };
}