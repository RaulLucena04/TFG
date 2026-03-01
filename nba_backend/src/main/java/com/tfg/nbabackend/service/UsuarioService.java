package com.tfg.nbabackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.nbabackend.model.Rol;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario guardarUsuario(Usuario usuario) {

        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        usuario.setPuntos(1000);

        usuario.setRol(Rol.USER);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario login(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
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

        usuario.setPassword(nuevaPassword);
        usuarioRepository.save(usuario);
    }
}
