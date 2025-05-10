package com.example.clanhubadv.Security.JWT;

import com.example.clanhubadv.service.exception.InvalidJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtCore {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.jwtLifeTime}")
    private long jwtLifeTime;

    private Key getSigningKey() {
        if (jwtSecret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 256 bits (32 characters) long.");
        }
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifeTime);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // ✅ Используем parseClaimsJws, а не parseClaimsJwt

            return true;

        } catch (JwtException e) {
            throw new InvalidJwtException("Invalid JWT token");
        }
    }

    public String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)  // ✅ Используем parseClaimsJws
                .getBody();

        return claims.getSubject();
    }
}
