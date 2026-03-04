package com.tfg.nbabackend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utilidad para generar hashes BCrypt de contraseñas
 * Úsalo para generar hashes que luego puedas usar en el script SQL
 */
public class PasswordHashGenerator {
    
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Contraseña por defecto
        String password = "password123";
        String hash = encoder.encode(password);
        
        System.out.println("Contraseña: " + password);
        System.out.println("Hash BCrypt: " + hash);
        System.out.println();
        
        // Verificar que funciona
        boolean matches = encoder.matches(password, hash);
        System.out.println("Verificación: " + (matches ? "✓ Correcto" : "✗ Error"));
        
        // Generar varios hashes para usar en el script
        System.out.println("\n--- Hashes para usar en SQL ---");
        for (int i = 0; i < 10; i++) {
            System.out.println("'" + encoder.encode(password) + "',");
        }
    }
}
