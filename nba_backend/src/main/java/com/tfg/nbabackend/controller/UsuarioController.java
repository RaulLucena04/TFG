package com.tfg.nbabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.nbabackend.exception.ApiError;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.service.UsuarioService;

import java.util.List;

/**
 * Controlador REST que gestiona las operaciones relacionadas con usuarios.
 * 
 * <p>Proporciona endpoints para registro, login, consulta y actualización de usuarios.
 * Todos los endpoints están habilitados para CORS desde cualquier origen.
 * 
 * @author TFG
 * @version 1.0
 */
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param usuario el usuario a registrar con username, email y password
     * @return ResponseEntity con el usuario creado si es exitoso, o error si falla
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    /**
     * Autentica un usuario con username y password.
     * 
     * @param usuario el usuario con username y password para autenticar
     * @return ResponseEntity con el usuario autenticado si las credenciales son correctas,
     *         o error 401 si son incorrectas
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario u = usuarioService.login(usuario.getUsername(), usuario.getPassword());

        if (u == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Credenciales incorrectas"));
        }

        return ResponseEntity.ok(u);
    }

    /**
     * Obtiene todos los usuarios del sistema.
     * 
     * @return lista de todos los usuarios
     */
    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerTodos();
    }

    /**
     * Obtiene un usuario por su identificador.
     * 
     * @param id el ID del usuario
     * @return ResponseEntity con el usuario si existe, o 404 si no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cambia la contraseña de un usuario.
     * 
     * @param id el ID del usuario
     * @param request objeto con la nueva contraseña
     * @return ResponseEntity con mensaje de éxito o error
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<?> cambiarPassword(
            @PathVariable Long id,
            @RequestBody PasswordRequest request) {

        usuarioService.cambiarPassword(id, request.getPassword());
        return ResponseEntity.ok(new ApiError("Contraseña actualizada"));
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * <p>Permite actualizar username, email y rol. Los campos no proporcionados
     * se mantienen sin cambios.
     * 
     * @param id el ID del usuario a actualizar
     * @param usuario objeto con los campos a actualizar
     * @return ResponseEntity con el usuario actualizado si es exitoso, o error si falla
     */
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
