package com.tfg.nbabackend.service;

import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.repository.JugadorRepository;
import com.tfg.nbabackend.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con jugadores.
 * 
 * <p>Este servicio se encarga de la lógica de negocio para jugadores, incluyendo
 * listado, consulta por equipo y guardado de jugadores.
 * 
 * @author TFG
 * @version 1.0
 */
@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    /**
     * Constructor del servicio de jugadores.
     * 
     * @param jugadorRepository repositorio de jugadores
     * @param equipoRepository repositorio de equipos
     */
    public JugadorService(JugadorRepository jugadorRepository,
                          EquipoRepository equipoRepository) {
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }

    /**
     * Obtiene todos los jugadores del sistema.
     * 
     * @return lista de todos los jugadores
     */
    public List<Jugador> listarTodos() {
        return jugadorRepository.findAll();
    }

    /**
     * Obtiene todos los jugadores de un equipo específico.
     * 
     * @param equipoId el ID del equipo
     * @return lista de jugadores del equipo
     * @throws RuntimeException si el equipo no existe
     */
    public List<Jugador> obtenerPorEquipo(Long equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return jugadorRepository.findByEquipo(equipo);
    }

    /**
     * Guarda un nuevo jugador o actualiza uno existente.
     * 
     * @param jugador el jugador a guardar
     * @return el jugador guardado
     */
    public Jugador guardar(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }
}