package com.tfg.nbabackend.repository;

import com.tfg.nbabackend.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositorio JPA para la entidad {@link Partido}.
 */
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    /**
     * Lista partidos cargando equipos en la misma consulta (evita proxies LAZY al serializar a JSON para clientes).
     */
    @Query("SELECT DISTINCT p FROM Partido p LEFT JOIN FETCH p.equipoLocal LEFT JOIN FETCH p.equipoVisitante")
    List<Partido> findAllWithEquipos();
}