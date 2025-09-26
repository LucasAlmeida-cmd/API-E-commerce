package com.example.e_commerce.config;

import com.example.e_commerce.usuario.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date; // IMPORTANTE: Importar java.util.Date

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        SecretKey chave = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        Instant agora = Instant.now();
        Instant expiracao = getDataExpiracao().toInstant(ZoneOffset.of("-03:00"));

        return Jwts.builder()
                .setIssuer("API E-commerce")
                .setSubject(usuario.getEmail())
                .setIssuedAt(Date.from(agora)) // <-- CORREÇÃO APLICADA
                .setExpiration(Date.from(expiracao)) // <-- CORREÇÃO APLICADA
                .signWith(chave, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String tokenJWT) {
        SecretKey chave = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        try {
            return Jwts.parser()
                    .setSigningKey(chave)
                    .build()
                    .parseClaimsJws(tokenJWT)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private LocalDateTime getDataExpiracao() {
        return LocalDateTime.now().plusHours(2);
    }
}