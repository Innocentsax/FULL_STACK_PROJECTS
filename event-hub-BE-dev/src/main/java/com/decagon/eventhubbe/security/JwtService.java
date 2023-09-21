package com.decagon.eventhubbe.security;

import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.JwtToken;
import com.decagon.eventhubbe.domain.repository.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenRepository jwtTokenRepository;
    @Value("${jwt.expiration.access-token}")
    private long access_expiration;
    @Value("${jwt.expiration.refresh-token}")
    private long refresh_expiration;
    private String generateSecret(){
        return DatatypeConverter.printBase64Binary(new byte[512/8]);
    }

    private Key generateKey(){
        byte[] secretKeyInBytes = DatatypeConverter.parseBase64Binary(generateSecret());
        return new SecretKeySpec(secretKeyInBytes, "HmacSHA512");
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + access_expiration))
                .signWith(generateKey(), SignatureAlgorithm.HS512)
                .compact();

    }
    public String generateRefreshToken(Authentication authentication){
        String username = authentication.getName();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refresh_expiration))
                .signWith(generateKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    public void revokeAllUserTokens(AppUser appUser){
        List<JwtToken> jwtTokens = jwtTokenRepository.findAllByAppUser(appUser);
        if (!jwtTokens.isEmpty()) {
            jwtTokens.forEach(jwtToken -> {
                jwtToken.setExpired(true);
                jwtToken.setRevoked(true);
            });
            jwtTokenRepository.saveAll(jwtTokens);
        }

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}