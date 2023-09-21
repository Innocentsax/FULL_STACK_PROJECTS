package com.decagon.eventhubbe.security;

import com.decagon.eventhubbe.domain.entities.JwtToken;
import com.decagon.eventhubbe.domain.repository.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final JwtTokenRepository jwtTokenRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String accessToken;
        String authHeader = request.getHeader("Authorization");

        if(authHeader!=null&&authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
            JwtToken jwtToken = jwtTokenRepository.findByAccessToken(accessToken);
            if(jwtToken != null){
                jwtToken.setExpired(true);
                jwtToken.setRevoked(true);
                jwtTokenRepository.save(jwtToken);
            }
        }
    }
}
