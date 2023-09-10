package com.hrsupportcentresq014.security_config.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {
    @Value("${application.jwt.security.secretKey}")
    String secretKey;
    @Value("${application.jwt.security.expiration}")
    long expiration;


    //Method to extract Username
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

    //Method to extract the expiration of the Jwt
    public Date getExpiration(String jwtToken){
        return extractClaims(jwtToken, Claims::getExpiration);
    }
    public Date getIssuedAt(String jwtToken){
        return extractClaims(jwtToken, Claims::getIssuedAt);
    }

    //Method to check if token is expired
    public boolean isTokenExpired(String jwtToken){
        return getExpiration(jwtToken).before(new Date());
    }

    //Method to check if token is valid
    public boolean isTokenValid(
            String jwtToken, UserDetails userDetails) {
        String username = extractUsername(jwtToken);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(jwtToken));
    }

    //Building the token
    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration){
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.putAll(extraClaims);
        claims.put("iat", new Date(System.currentTimeMillis()));
        claims.put("exp", new Date(System.currentTimeMillis() + expiration));
        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Helper method to help generate Tokens with extra claims
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return buildToken(extraClaims, userDetails, expiration);
    }

    //Method to generate the JwtToken needed to Authenticate users
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
}
