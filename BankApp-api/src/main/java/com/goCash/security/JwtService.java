package com.goCash.security;

import com.goCash.exception.UserNotFoundException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Slf4j
@Component
public class JwtService {

    @Value("${jwt.secretToken}")
    private String secretToken;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretToken);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + 60000 * 24);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    public String getUserNameFromToken(String token) {
        log.info("JwtService is called to extract the userEmail from the JWT");
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();


    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new UserNotFoundException("Invalid Token");
        }
    }
}
