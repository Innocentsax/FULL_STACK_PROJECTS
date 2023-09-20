package com.decagon.borrowerservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpirationMs;

    private final String secretKey="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String generateJwtToken(String userType, String userId, String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationMs);
        return Jwts.builder()
                .claim("userType", userType)
                .claim("userId", userId)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public Claims extractAllClaims(String jwtToken){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    // Helper method to get the signing key
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

    public String getEmailFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Invalid token or expired
        }
        return false;
    }

    public Claims parseJwtToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // Invalid token or parsing error
            return null;
        }
    }

    }
