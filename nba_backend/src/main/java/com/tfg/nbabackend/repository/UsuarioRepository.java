package com.tfg.nbabackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.nbabackend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
