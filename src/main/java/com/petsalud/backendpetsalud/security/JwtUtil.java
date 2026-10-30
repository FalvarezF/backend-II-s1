
package com.petsalud.backendpetsalud.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final SecretKey claveSecreta;
    private final long tiempoExpiracion = 3600000;

    public JwtUtil(@Value("${jwt.secret}") String secreto) {
        if (secreto.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException(
                "JWT_SECRET debe tener al menos 32 bytes"
            );
        }

        this.claveSecreta = Keys.hmacShaKeyFor(
            secreto.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Generar un token JWT
    public String generarToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(
                    System.currentTimeMillis() + tiempoExpiracion
                ))
                .signWith(claveSecreta)
                .compact();
    }

    // Extraer el nombre de usuario del token
    public String obtenerUsername(String token) {
        return obtenerClaims(token).getSubject();
    }

    // Validar el token
    public boolean validarToken(String token) {
        try {
            obtenerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Obtener los datos del token
    private Claims obtenerClaims(String token) {
        return Jwts.parser()
                .verifyWith(claveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
