package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.service.JugadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que gestiona las operaciones relacionadas con jugadores.
 * 
 * <p>Proporciona endpoints para listar jugadores y obtener jugadores por equipo.
 * Todos los endpoints están habilitados para CORS desde cualquier origen.
 * 
 * @author TFG
 * @version 1.0
 */
@RestController
@RequestMapping("/jugadores")
@CrossOrigin(origins = "*")
public class JugadorController {

    private final JugadorService jugadorService;

    /**
     * Constructor del controlador de jugadores.
     * 
     * @param jugadorService servicio de jugadores
     */
    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    /**
     * Obtiene todos los jugadores del sistema.
     * 
     * @return lista de todos los jugadores
     */
    @GetMapping
    public List<Jugador> listarJugadores() {
        return jugadorService.listarTodos();
    }

    /**
     * Obtiene todos los jugadores de un equipo específico.
     * 
     * @param equipoId el ID del equipo
     * @return lista de jugadores del equipo
     */
    @GetMapping("/equipo/{equipoId}")
    public List<Jugador> jugadoresPorEquipo(@PathVariable Long equipoId) {
        return jugadorService.obtenerPorEquipo(equipoId);
    }

    /**
     * Crea un nuevo jugador en el sistema.
     * 
     * <p>Principalmente usado desde el panel de administración.
     * 
     * @param jugador el jugador a crear
     * @return el jugador creado
     */
    @PostMapping
    public Jugador crearJugador(@RequestBody Jugador jugador) {
        return jugadorService.guardar(jugador);
    }
}