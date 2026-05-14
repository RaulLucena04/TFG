package com.tfg.nbabackend.repository;

import com.tfg.nbabackend.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad {@link Partido}.
 */
public interface PartidoRepository extends JpaRepository<Partido, Long> {
}