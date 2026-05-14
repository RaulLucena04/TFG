package com.tfg.nbabackend.repository;

import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad {@link Jugador}.
 */
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    List<Jugador> findByEquipo(Equipo equipo);
}