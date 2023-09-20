package com.example.transactionservice.securityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JWTUtils {


    @Value("${application.jwt.security.secretKey}")
    String secretKey;

    public Claims extractAllClaims(String jwtToken){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte [] byteKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(byteKey);
    }

    public String getUserIdFromToken(String token){
        // Get the claims (payload) from the parsed token
        Claims claims = extractAllClaims(token);
        // Extract and return the userId from the claims
        String userIdAsString = claims.get("userId", String.class);
        return userIdAsString;
    }

    public String getUserTypeFromToken(String token){
        Claims claims = extractAllClaims(token);
        return claims.get("userType", String.class);
    }
}
