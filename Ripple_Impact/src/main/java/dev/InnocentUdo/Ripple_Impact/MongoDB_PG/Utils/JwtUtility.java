package dev.InnocentUdo.Ripple_Impact.MongoDB_PG.Utils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.*;
import java.util.Date;


@Component
public class JwtUtility {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60 * 10))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        }
        catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        }
        catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        }
        catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        }
        catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

}
