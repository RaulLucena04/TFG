package com.tfg.nbabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario u = usuarioService.login(usuario.getUsername(), usuario.getPassword());

        if (u == null) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        return ResponseEntity.ok(u);
    }

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerTodos();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> cambiarPassword(
            @PathVariable Long id,
            @RequestBody PasswordRequest request) {

        usuarioService.cambiarPassword(id, request.getPassword());
        return ResponseEntity.ok("Contraseña actualizada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Actualizar solo los campos permitidos (NO actualizar contraseña aquí)
            // La contraseña solo se actualiza mediante el endpoint /{id}/password
            if (usuario.getUsername() != null) {
                usuarioExistente.setUsername(usuario.getUsername());
            }
            if (usuario.getEmail() != null) {
                usuarioExistente.setEmail(usuario.getEmail());
            }
            if (usuario.getRol() != null) {
                usuarioExistente.setRol(usuario.getRol());
            }
            // La contraseña se ignora intencionalmente - usar endpoint específico para cambiarla

            Usuario actualizado = usuarioService.actualizarUsuario(usuarioExistente);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
