package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.TokenRepository;
import com.hrsupportcentresq014.security_config.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private  final JwtUtils jwtUtils;
    private final EmployeeRepository employeeRepository;
    private final TokenRepository tokenRepository;

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
        var token = tokenRepository.findByJwtToken(jwt)
                .orElse(null);
        if (token == null && user == null) {
            return;
        }
        user.setLoggedIn(false);
        employeeRepository.save(user);
        token.setExpired(true);
        token.setRevoked(true);
        tokenRepository.save(token);
    }
}
