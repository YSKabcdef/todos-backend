package com.portfolio.todo_management.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;   

    // generate jwt
    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expiryDate = new Date (currentDate.getTime()+jwtExpirationDate);
        
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(key()).compact();
        return token;
    }

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser()
        .verifyWith(key())
        .build()
        .parseSignedClaims(token)
        .getPayload();
        String username = claims.getSubject();

        return username;
    }

    public boolean validateToken(String token){
        Jwts.parser()
        .verifyWith(key())
        .build()
        .parse(token);
        return true;
    }
}
