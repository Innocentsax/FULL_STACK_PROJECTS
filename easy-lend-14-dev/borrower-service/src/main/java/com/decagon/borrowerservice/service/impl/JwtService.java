package com.decagon.borrowerservice.service.impl;

import com.decagon.borrowerservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private JwtUtils jwtUtils;

    public void processJwtToken(String jwtToken) {
        String userId = null;
        String email = null;

        Claims claims = jwtUtils.parseJwtToken(jwtToken);
        if (claims != null) {
            userId = claims.get("userId", String.class);
            email = claims.getSubject();
        }

        if (userId != null && email != null) {

            System.out.println("User ID: " + userId);
            System.out.println("Email: " + email);
        } else {
            System.out.println("Invalid JWT token or missing claims.");
        }
    }
}
