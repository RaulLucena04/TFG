package com.tfg.nbabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.nbabackend.model.Usuario;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad {@link Usuario}.
 *
 * <p>Incluye utilidades para login/registro: búsqueda por username/email y checks de existencia.</p>
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
