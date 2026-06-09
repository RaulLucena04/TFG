package com.tfg.nbabackend.repository;

import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio JPA para la entidad {@link Apuesta}.
 *
 * <p>Proporciona consultas derivadas para recuperar apuestas por usuario y por partido.</p>
 */
public interface ApuestaRepository extends JpaRepository<Apuesta, Long> {

    List<Apuesta> findByUsuario(Usuario usuario);

    List<Apuesta> findByPartido(Partido partido);

    /**
     * Apuestas de un usuario con partido y equipos resueltos (JSON estable para clientes socket).
     */
    @Query("SELECT DISTINCT a FROM Apuesta a "
            + "JOIN FETCH a.usuario u "
            + "JOIN FETCH a.partido p "
            + "LEFT JOIN FETCH p.equipoLocal LEFT JOIN FETCH p.equipoVisitante "
            + "WHERE u.id = :userId")
    List<Apuesta> findAllByUsuarioIdWithRelations(@Param("userId") Long userId);
}