package com.decagon.adire.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.decagon.adire.dto.response.TokenResponse;
import com.decagon.adire.exception.UnAuthorizedException;
import com.decagon.adire.utils.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Slf4j
@Component
public class TokenProvider {

public static TokenResponse generate(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    String userName = user.getUsername();

    Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET.getBytes());
    String access_token = JWT.create()
            .withSubject(userName)
            .withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME))
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withClaim("roles", authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList())
            ).sign(algorithm);

    return new TokenResponse(access_token);
}
    public static UsernamePasswordAuthenticationToken validateTokenAndGetJws(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET.getBytes());
            JWTVerifier verify = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verify.verify(token);
            String username = decodedJWT.getSubject();
            log.info("username from jwt token: {}", username);
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });

            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new UnAuthorizedException("Unauthorized invalid token", HttpStatus.UNAUTHORIZED);
        }
    }


    public static boolean doesBothStringMatch(String firstText, String secondText){
        if (Objects.nonNull(firstText) && Objects.nonNull(secondText)) {
            return Objects.equals(firstText, secondText);
        }
        return false;
    }

    public static String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


}
