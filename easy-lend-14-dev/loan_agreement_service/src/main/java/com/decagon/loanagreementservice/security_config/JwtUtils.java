package com.decagon.loanagreementservice.security_config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springdoc.webmvc.core.fn.SpringdocRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
@Component
public class  JwtUtils {
    @Value("${application.jwt.security.secretKey}")
    String secretKey;

    //Method to check if token is expired
    public boolean isTokenExpired(String jwtToken){
        return getExpiration(jwtToken).before(new Date());
    }

    private Date getExpiration(String jwtToken) {
        return extractClaims(jwtToken, Claims::getExpiration);
    }

    //Method to check if token is valid
    public boolean isTokenValid(String jwtToken) {
        Date expirationDate = getExpiration(jwtToken);
        if (expirationDate != null && expirationDate.before(new Date())) {
            return false;
        }
        return true;
    }
//
    public String extractUsername(String jwtToken){
        return extractClaims(jwtToken, Claims::getSubject);
    }

    //Method to extractClaims using a functional interface
    public <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    //Method to extract the all claims related to the token
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
}
