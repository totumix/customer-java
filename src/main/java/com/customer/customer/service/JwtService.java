package com.customer.customer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = generateKeyFromPassword("ejercicioentrevista"); // Clave generada a partir de la palabra
    private static final long EXPIRATION_TIME = 3600000; // 1 hora

    // Generar la clave de 256 bits a partir de la palabra 'ejercicioentrevista' usando SHA-256
    private static String generateKeyFromPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            // Codificar en Base64 para usarlo en JWT
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando la clave a partir de la palabra", e);
        }
    }

    public Map<String, String> generateToken(String nombre, String correo) {
        String token = Jwts.builder()
                .claim("nombre", nombre)
                .claim("correo", correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);  // Devuelve el token dentro de un mapa
        return response;
    }

}

