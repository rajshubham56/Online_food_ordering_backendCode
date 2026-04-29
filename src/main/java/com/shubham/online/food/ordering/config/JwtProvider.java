package com.shubham.online.food.ordering.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))  // Fix 3: configurable
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(getKey())                          // Fix 1: lazy key
                .compact();
    }

    public String getEmailFromJwtToken(String jwt) {
        // Fix 4: "Bearer " ka proper check
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }


        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return String.valueOf(claims.get("email"));

        }  catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT Error: " + e.getMessage()); // debug ke liye
            return null;
        }
    }


    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}