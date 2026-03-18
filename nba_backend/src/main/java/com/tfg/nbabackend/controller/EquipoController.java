package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.dto.EquipoEstadisticasDTO;
import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.service.EquipoEstadisticasService;
import com.tfg.nbabackend.service.EquipoService;
import com.tfg.nbabackend.service.JugadorService;
import com.tfg.nbabackend.service.PartidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que gestiona las operaciones relacionadas con equipos.
 * 
 * <p>Proporciona endpoints para listar equipos, obtener estadísticas, jugadores
 * y partidos de cada equipo. Todos los endpoints están habilitados para CORS desde cualquier origen.
 * 
 * @author TFG
 * @version 1.0
 */
@RestController
@RequestMapping("/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {

    private final EquipoService equipoService;
    private final JugadorService jugadorService;
    private final PartidoService partidoService;
    private final EquipoEstadisticasService equipoEstadisticasService;

    /**
     * Constructor del controlador de equipos.
     * 
     * @param equipoService servicio de equipos
     * @param jugadorService servicio de jugadores
     * @param partidoService servicio de partidos
     * @param equipoEstadisticasService servicio de estadísticas de equipos
     */
    public EquipoController(EquipoService equipoService,
                            JugadorService jugadorService,
                            PartidoService partidoService,
                            EquipoEstadisticasService equipoEstadisticasService) {
        this.equipoService = equipoService;
        this.jugadorService = jugadorService;
        this.partidoService = partidoService;
        this.equipoEstadisticasService = equipoEstadisticasService;
    }

    /**
     * Obtiene las estadísticas calculadas de un equipo.
     * 
     * <p>Incluye record (victorias/derrotas) y promedios de jugadores (PPG, RPG, APG).
     * 
     * @param id el ID del equipo
     * @return estadísticas del equipo
     */
    @GetMapping("/{id}/estadisticas")
    public EquipoEstadisticasDTO obtenerEstadisticas(@PathVariable Long id) {
        return equipoEstadisticasService.calcularEstadisticas(id);
    }

    /**
     * Obtiene todos los equipos del sistema.
     * 
     * @return lista de todos los equipos
     */
    @GetMapping
    public List<Equipo> listarEquipos() {
        return equipoService.listarTodos();
    }

    /**
     * Obtiene un equipo por su identificador.
     * 
     * @param id el ID del equipo
     * @return el equipo encontrado
     * @throws RuntimeException si el equipo no existe
     */
    @GetMapping("/{id}")
    public Equipo obtenerEquipo(@PathVariable Long id) {
        return equipoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    /**
     * Obtiene todos los jugadores de un equipo.
     * 
     * @param id el ID del equipo
     * @return lista de jugadores del equipo
     */
    @GetMapping("/{id}/jugadores")
    public List<Jugador> obtenerJugadores(@PathVariable Long id) {
        return jugadorService.obtenerPorEquipo(id);
    }

    /**
     * Obtiene todos los partidos en los que participa un equipo.
     * 
     * <p>Incluye tanto los partidos donde el equipo juega como local
     * como los que juega como visitante.
     * 
     * @param id el ID del equipo
     * @return lista de partidos del equipo
     */
    @GetMapping("/{id}/partidos")
    public List<Partido> obtenerPartidos(@PathVariable Long id) {
        return partidoService.listarPartidos().stream()
                .filter(p -> p.getEquipoLocal().getId().equals(id) ||
                             p.getEquipoVisitante().getId().equals(id))
                .toList();
    }

    /**
     * Crea un nuevo equipo en el sistema.
     * 
     * @param equipo el equipo a crear
     * @return el equipo creado
     */
    @PostMapping
    public Equipo crearEquipo(@RequestBody Equipo equipo) {
        return equipoService.guardar(equipo);
    }
}