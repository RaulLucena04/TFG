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
}
