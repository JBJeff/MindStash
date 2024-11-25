// package com.restApi.RestApi.Basics.JWT;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;

// import java.security.Key;
// import java.util.Date;

// import org.springframework.stereotype.Component;

// @Component
// public class JWTUtility {

//     private final String SECRET_KEY = "secret-key"; // Geheimer Schlüssel für Signatur

//     // Token generieren
//     public String generateToken(String userId) {
//         return Jwts.builder()
//                 .setSubject(userId) // Setze die Benutzer-ID als Subject
//                 .setIssuedAt(new Date()) // Erstellungsdatum
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Ablaufzeit (10 Stunden)
//                 .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // Algorithmus und Schlüssel
//                 .compact();
//     }

//     // Token validieren und Benutzer-ID extrahieren
//     public String validateTokenAndGetUserId(String token) {
//         try {
//             Claims claims = Jwts.parser()
//                     .setSigningKey(SECRET_KEY) // Signatur überprüfen
//                     .parseClaimsJws(token)
//                     .getBody();
//             return claims.getSubject(); // Benutzer-ID auslesen
//         } catch (ExpiredJwtException | SignatureException | MalformedJwtException e) {
//             throw new RuntimeException("Ungültiges oder abgelaufenes Token");
//         }
//     }
// }
