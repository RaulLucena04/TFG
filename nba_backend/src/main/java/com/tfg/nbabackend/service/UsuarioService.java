package com.tfg.nbabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPuntos(1000); // puntos iniciales
        return usuarioRepository.save(usuario);
    }
}
