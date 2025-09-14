package com.tanmay.e_commerce_application.auth_service.Utility;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    static String SECRET = "vN8aZf2tQxKkN9pPsh9z7kdq3Fq0wW6hZ3H2kJqX2A8=";
    static int EXPIRATION_TIME = 1000 * 60 * 60; 
    static Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("roles", userDetails.getAuthorities())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();
    }

    public static String extractUsername(String token) {
        return extractAllClaims(token)
            .getSubject();
    }


    public static boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public static boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token)
            .getExpiration();
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}   