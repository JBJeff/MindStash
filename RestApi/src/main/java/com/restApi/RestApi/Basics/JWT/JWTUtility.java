package com.restApi.RestApi.Basics.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JWTUtility {

    private static final String SECRET_KEY = "YourSuperSecretKeyForJWTGeneration123456789"; // Mindestens 256-Bit Schlüssel
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 Stunden

    private final Key key;

    public JWTUtility() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Token erstellen
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Token validieren
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Token-Validierungsfehler: " + e.getMessage());
            return false;
        }
    }

    // Benutzer-ID aus dem Token extrahieren
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
