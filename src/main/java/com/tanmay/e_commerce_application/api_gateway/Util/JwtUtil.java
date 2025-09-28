package com.tanmay.e_commerce_application.api_gateway.Util;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private Integer EXPIRATION;

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUsername(token);
        return userDetails.getUsername().equals(userName) && !isTokenExpired(token);
    }

}
