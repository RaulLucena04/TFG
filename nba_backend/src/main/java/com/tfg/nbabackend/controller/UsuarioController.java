package com.tfg.nbabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.nbabackend.exception.ApiError;
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
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario u = usuarioService.login(usuario.getUsername(), usuario.getPassword());

        if (u == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Credenciales incorrectas"));
        }

        return ResponseEntity.ok(u);
    }

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> cambiarPassword(
            @PathVariable Long id,
            @RequestBody PasswordRequest request) {

        usuarioService.cambiarPassword(id, request.getPassword());
        return ResponseEntity.ok(new ApiError("Contraseña actualizada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (usuario.getUsername() != null) {
                usuarioExistente.setUsername(usuario.getUsername());
            }
            if (usuario.getEmail() != null) {
                usuarioExistente.setEmail(usuario.getEmail());
            }
            if (usuario.getRol() != null) {
                usuarioExistente.setRol(usuario.getRol());
            }

            Usuario actualizado = usuarioService.actualizarUsuario(usuarioExistente);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

}
