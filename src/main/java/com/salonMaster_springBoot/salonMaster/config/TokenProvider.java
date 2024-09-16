package com.salonMaster_springBoot.salonMaster.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {
    // La clé utilisée pour signer les tokens
    private final Key key;
    // Durée de validité du token en millisecondes (1 jour)
    private final int tokenValidityInMillis = 86400000;

    public TokenProvider(@Value("${jwt.secret}") String secret) {
        if (secret == null || secret.isEmpty()) {
            // Si le secret est vide ou non défini, génère une nouvelle clé et l'affiche sur
            // la console
            String generatedKey = generateSecretKey();
            System.out.println("Generated JWT Secret Key: " + generatedKey);
            // Utilise la nouvelle clé pour initialiser l'attribut key
            this.key = Keys.hmacShaKeyFor(generatedKey.getBytes());
        } else {
            // Si le secret est défini, l'utilise pour initialiser l'attribut key
            this.key = Keys.hmacShaKeyFor(secret.getBytes());
        }
    }

    // Cette méthode génère une nouvelle clé secrète et la retourne
    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    // Génère un token JWT pour un utilisateur donné basé sur son email
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenValidityInMillis);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Récupère l'email à partir du token JWT
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Vérifie la validité du token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            System.out.println("Token is valid");
            return true;
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }
}
