package com.decagon.kindredhairapigrp1.utils.security;

import com.decagon.kindredhairapigrp1.models.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The JWT Utility class consists of methods that carry out various
 * JWT functionalities
 */
@Service
public class JwtUtil {
    @Value("#{environment['jwt.secret']}")
    private String  SECRET_KEY;

    /**
     * Method returns the subject used to create a token
     * @param token
     * @return
     * @throws MalformedJwtException
     */
    public String extractSubject(String token) throws MalformedJwtException{
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) throws MalformedJwtException{
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws MalformedJwtException{
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws MalformedJwtException {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Method checks if the token passed to it is  expired
     * @param token
     * @return
     * @throws JwtException
     */
    public Boolean isTokenExpired(String token) throws JwtException {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Method generates and returns login token
     * @param userDetails
     * @return
     */
    public String generateLoginToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createLoginToken(claims, userDetails.getEmail());
    }

    /**
     * Method creates and returns generated token set to expire in specified time
     * which is in minutes
     * @param detail
     * @param minutes
     * @return
     */
    public String generateToken(String detail, int minutes) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, detail, minutes);
    }

    /**
     * Method creates login token and sets token to expire after a year
     * @param claims
     * @param subject
     * @return
     */
    private String createLoginToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 365))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Method creates token and sets token to expire after a specified time passed as minutes
     * @param claims
     * @param subject
     * @return
     */
    private String createToken(Map<String, Object> claims, String subject, int minutes){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * minutes))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Method validates a token by expiring date and the user details passed to it
     * @param token
     * @param userDetails
     * @return
     * @throws MalformedJwtException
     */
    public Boolean validateToken(String token, String userDetails) throws MalformedJwtException{
        final String subject = extractSubject(token);
        return (subject.equals(userDetails) && !isTokenExpired(token));
    }
}


