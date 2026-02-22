package com.tfg.nbabackend.repository;

import com.tfg.nbabackend.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
}