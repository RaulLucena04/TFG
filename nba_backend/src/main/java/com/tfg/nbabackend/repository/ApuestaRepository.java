package com.tfg.nbabackend.repository;

import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad {@link Apuesta}.
 *
 * <p>Proporciona consultas derivadas para recuperar apuestas por usuario y por partido.</p>
 */
public interface ApuestaRepository extends JpaRepository<Apuesta, Long> {

    List<Apuesta> findByUsuario(Usuario usuario);

    List<Apuesta> findByPartido(Partido partido);
}