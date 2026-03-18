package com.tfg.nbabackend.service;

import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con equipos.
 * 
 * <p>Este servicio se encarga de la lógica de negocio para equipos, incluyendo
 * listado, consulta y guardado de equipos.
 * 
 * @author TFG
 * @version 1.0
 */
@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    /**
     * Constructor del servicio de equipos.
     * 
     * @param equipoRepository repositorio de equipos
     */
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    /**
     * Obtiene todos los equipos del sistema.
     * 
     * @return lista de todos los equipos
     */
    public List<Equipo> listarTodos() {
        return equipoRepository.findAll();
    }

    /**
     * Obtiene un equipo por su identificador.
     * 
     * @param id el ID del equipo
     * @return Optional con el equipo si existe, vacío si no
     */
    public Optional<Equipo> obtenerPorId(Long id) {
        return equipoRepository.findById(id);
    }

    /**
     * Guarda un nuevo equipo o actualiza uno existente.
     * 
     * @param equipo el equipo a guardar
     * @return el equipo guardado
     */
    public Equipo guardar(Equipo equipo) {
        return equipoRepository.save(equipo);
    }
}