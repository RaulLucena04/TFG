package com.tfg.nbabackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfg.nbabackend.model.Rol;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario guardarUsuario(Usuario usuario) {

        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Validar longitud mínima de contraseña (6 caracteres)
        String passwordPlano = usuario.getPassword();
        if (passwordPlano == null || passwordPlano.length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }

        // Hashear la contraseña antes de guardar
        String passwordHasheado = passwordEncoder.encode(passwordPlano);
        usuario.setPassword(passwordHasheado);

        usuario.setPuntos(1000);

        usuario.setRol(Rol.USER);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario login(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .filter(u -> {
                    // Verificar si la contraseña está hasheada (empieza con $2a$ o $2b$)
                    String passwordAlmacenado = u.getPassword();
                    if (passwordAlmacenado != null && (passwordAlmacenado.startsWith("$2a$") || passwordAlmacenado.startsWith("$2b$"))) {
                        // Contraseña hasheada, usar BCrypt para comparar
                        return passwordEncoder.matches(password, passwordAlmacenado);
                    } else {
                        // Contraseña antigua en texto plano (para migración)
                        // Comparar en texto plano pero hashear al guardar
                        boolean coincide = passwordAlmacenado != null && passwordAlmacenado.equals(password);
                        if (coincide) {
                            // Hashear la contraseña antigua y actualizarla
                            String passwordHasheado = passwordEncoder.encode(password);
                            u.setPassword(passwordHasheado);
                            usuarioRepository.save(u);
                        }
                        return coincide;
                    }
                })
                .orElse(null);
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void cambiarPassword(Long id, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar longitud mínima de contraseña (6 caracteres)
        if (nuevaPassword == null || nuevaPassword.length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }

        // Hashear la nueva contraseña antes de guardar
        String passwordHasheado = passwordEncoder.encode(nuevaPassword);
        usuario.setPassword(passwordHasheado);
        usuarioRepository.save(usuario);
    }
}
