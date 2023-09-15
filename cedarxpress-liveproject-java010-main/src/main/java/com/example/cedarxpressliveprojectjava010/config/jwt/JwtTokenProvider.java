package com.example.cedarxpressliveprojectjava010.config.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-minutes}")
    private int JwtExpirationInMn;


    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + (long) JwtExpirationInMn * 60 * 1000);

        String token  = Jwts.builder()
                .setSubject(email)
                .claim("Authorities", authentication.getAuthorities())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return "Bearer "  + token;
    }

    public Jws<Claims> validateToken (String token){
        Jws<Claims> claimsJWS;
        try {
            claimsJWS = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);

        }catch (SignatureException ex){
            throw  new InvalidJwtTokenException(HttpStatus.BAD_REQUEST, "invalid jwt token");
        }
        return claimsJWS;
    }

    public Date getExpiryDate (String token){
        Jws<Claims>  claimsJws = validateToken(token);
        Claims claims = claimsJws.getBody();
        return claims.getExpiration();
    }
}
