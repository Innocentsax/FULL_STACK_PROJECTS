package com.decagon.lendingservice.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public  String  generateToken(String userId, String userType) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .setSubject(userId)
                .claim("userType", userType)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }
     public Claims parseToken(String token){
//        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
         return  Jwts.parserBuilder()
                 .setSigningKey(getSigningKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
     }
    public String getUserTypeFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userType", String.class);
    }
    public String extractUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", String.class);

    }
    private  Key getSigningKey(){
        byte [] byteKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(byteKey);
    }
}
