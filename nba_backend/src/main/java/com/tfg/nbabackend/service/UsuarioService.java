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

/**
 * Servicio que gestiona las operaciones relacionadas con usuarios.
 * 
 * <p>Este servicio se encarga de la lógica de negocio para usuarios, incluyendo
 * registro, autenticación, gestión de contraseñas y operaciones CRUD.
 * 
 * @author TFG
 * @version 1.0
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Guarda un nuevo usuario en el sistema.
     * 
     * <p>Valida que el username y email sean únicos, valida la longitud de la contraseña,
     * la hashea con BCrypt, asigna puntos iniciales (1000) y rol USER por defecto.
     * 
     * @param usuario el usuario a guardar
     * @return el usuario guardado con contraseña hasheada
     * @throws RuntimeException si el username o email ya existen, o si la contraseña es inválida
     */
    public Usuario guardarUsuario(Usuario usuario) {

        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        String passwordPlano = usuario.getPassword();
        if (passwordPlano == null || passwordPlano.length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }

        String passwordHasheado = passwordEncoder.encode(passwordPlano);
        usuario.setPassword(passwordHasheado);

        usuario.setPuntos(1000);

        usuario.setRol(Rol.USER);

        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene todos los usuarios del sistema.
     * 
     * @return lista de todos los usuarios
     */
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Autentica un usuario con username y contraseña.
     * 
     * <p>Compara la contraseña proporcionada con la almacenada. Si la contraseña
     * almacenada está en texto plano (migración), la hashea automáticamente.
     * 
     * @param username el nombre de usuario
     * @param password la contraseña en texto plano
     * @return el usuario si las credenciales son correctas, null en caso contrario
     */
    public Usuario login(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .filter(u -> {
                    String passwordAlmacenado = u.getPassword();
                    if (passwordAlmacenado != null && (passwordAlmacenado.startsWith("$2a$") || passwordAlmacenado.startsWith("$2b$"))) {
                        return passwordEncoder.matches(password, passwordAlmacenado);
                    } else {
                        boolean coincide = passwordAlmacenado != null && passwordAlmacenado.equals(password);
                        if (coincide) {
                            String passwordHasheado = passwordEncoder.encode(password);
                            u.setPassword(passwordHasheado);
                            usuarioRepository.save(u);
                        }
                        return coincide;
                    }
                })
                .orElse(null);
    }

    /**
     * Obtiene un usuario por su identificador.
     * 
     * @param id el ID del usuario
     * @return Optional con el usuario si existe, vacío si no
     */
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param usuario el usuario con los datos actualizados
     * @return el usuario actualizado
     */
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id el ID del usuario a eliminar
     */
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Cambia la contraseña de un usuario.
     * 
     * <p>Valida que la nueva contraseña tenga al menos 6 caracteres y la hashea
     * antes de guardarla.
     * 
     * @param id el ID del usuario
     * @param nuevaPassword la nueva contraseña en texto plano
     * @throws RuntimeException si el usuario no existe o la contraseña es inválida
     */
    public void cambiarPassword(Long id, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (nuevaPassword == null || nuevaPassword.length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }

        String passwordHasheado = passwordEncoder.encode(nuevaPassword);
        usuario.setPassword(passwordHasheado);
        usuarioRepository.save(usuario);
    }
}
