package com.tfg.nbabackend.repository;

import com.tfg.nbabackend.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad {@link Equipo}.
 */
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
}