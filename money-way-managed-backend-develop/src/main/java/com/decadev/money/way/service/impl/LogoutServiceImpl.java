package com.decadev.money.way.service.impl;

import com.decadev.money.way.repository.JwtTokenRepository;
import com.decadev.money.way.repository.UserRepository;
import com.decadev.money.way.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private  final JwtService jwtUtils;
    private final UserRepository employeeRepository;
    private final JwtTokenRepository jwtTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwt);

        var user = employeeRepository.findByEmail(userEmail)
                .orElse(null);
        var token = jwtTokenRepository.findByToken(jwt)
                .orElse(null);
        if (token == null && user == null) {
            return;
        }
        assert user != null;
        employeeRepository.save(user);

        assert token != null;
        token.setExpired(true);
        token.setRevoked(true);
        jwtTokenRepository.save(token);
    }
}
