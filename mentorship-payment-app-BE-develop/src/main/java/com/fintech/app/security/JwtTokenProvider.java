package com.fintech.app.security;

import com.fintech.app.model.User;
import com.fintech.app.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

//    @Value("${jwt.secret.key}")
    private final String jwtSecret = "secretKey123";

    private final UserRepository userRepository;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findUserByEmail(username);
        String name = user == null ? "" : user.getFirstName() + " " + user.getLastName();
        Date currentDate = new Date();
        long jwtExpirationInMillis = 30 * 60 * 1000;
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMillis);

        return Jwts.builder()
                .setSubject(username)
                .addClaims(Map.of("name", name))
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generatePasswordResetToken(String email){
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + 900000);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException exception){
            throw new RuntimeException("Invalid JWT_Token");
        }
    }
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

}

