package com.customer.customer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setup() {
        jwtService = new JwtService();
    }

    @Test
    public void testGenerateToken_createsValidToken() {
        // Datos de entrada
        String nombre = "Juan";
        String correo = "juan@example.com";

        Map<String, String> result = jwtService.generateToken(nombre, correo);

        assertNotNull(result);

        String token = result.get("token");
        assertNotNull(token);

        assertFalse(token.isEmpty());

        assertTrue(token.contains("."));
    }

    @Test
    public void testGenerateToken_containsCorrectClaims() throws NoSuchFieldException, IllegalAccessException {
        String nombre = "Juan";
        String correo = "juan@example.com";
        Map<String, String> result = jwtService.generateToken(nombre, correo);

        String token = result.get("token");

        Field secretKeyField = JwtService.class.getDeclaredField("SECRET_KEY");
        secretKeyField.setAccessible(true);
        String secretKey = (String) secretKeyField.get(jwtService);

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseSignedClaims(token)
                .getBody();

        assertEquals("Juan", claims.get("nombre"));
        assertEquals("juan@example.com", claims.get("correo"));
    }

    @Test
    public void testTokenExpiration() throws NoSuchFieldException, IllegalAccessException {
        // Datos de entrada
        String nombre = "Juan";
        String correo = "juan@example.com";

        // Llamada al método que estamos probando
        Map<String, String> result = jwtService.generateToken(nombre, correo);

        String token = result.get("token");

        Field secretKeyField = JwtService.class.getDeclaredField("SECRET_KEY");
        secretKeyField.setAccessible(true);
        String secretKey = (String) secretKeyField.get(jwtService);

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()// Usamos la clave secreta recuperada por reflexión
                .parseClaimsJws(token)
                .getBody();

        assertNotNull(claims.getExpiration());

        assertTrue(claims.getExpiration().after(new java.util.Date()));
    }
}
