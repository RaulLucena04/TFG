package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.dto.EquipoConEstadisticasDTO;
import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.service.EquipoEstadisticasService;
import com.tfg.nbabackend.service.EquipoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que proporciona equipos con estadísticas calculadas.
 * 
 * <p>Endpoint alternativo para evitar conflicto de rutas con /equipos/{id}.
 * Devuelve todos los equipos con sus estadísticas calculadas (record, PPG, RPG, APG).
 * Todos los endpoints están habilitados para CORS desde cualquier origen.
 * 
 * @author TFG
 * @version 1.0
 */
@RestController
@RequestMapping("/equipos-con-estadisticas")
@CrossOrigin(origins = "*")
public class EquiposConEstadisticasController {

    private final EquipoService equipoService;
    private final EquipoEstadisticasService equipoEstadisticasService;

    /**
     * Constructor del controlador de equipos con estadísticas.
     * 
     * @param equipoService servicio de equipos
     * @param equipoEstadisticasService servicio de estadísticas de equipos
     */
    public EquiposConEstadisticasController(EquipoService equipoService,
                                            EquipoEstadisticasService equipoEstadisticasService) {
        this.equipoService = equipoService;
        this.equipoEstadisticasService = equipoEstadisticasService;
    }

    /**
     * Obtiene todos los equipos con sus estadísticas calculadas.
     * 
     * <p>Para cada equipo, calcula:
     * <ul>
     *   <li>Record (victorias y derrotas) basado en partidos finalizados</li>
     *   <li>PPG (puntos por partido) promedio de jugadores</li>
     *   <li>RPG (rebotes por partido) promedio de jugadores</li>
     *   <li>APG (asistencias por partido) promedio de jugadores</li>
     * </ul>
     * 
     * @return lista de equipos con estadísticas calculadas
     */
    @GetMapping
    public List<EquipoConEstadisticasDTO> listar() {
        List<Equipo> equipos = equipoService.listarTodos();
        return equipos.stream()
                .map(e -> {
                    var stats = equipoEstadisticasService.calcularEstadisticas(e.getId());
                    return new EquipoConEstadisticasDTO(
                            e.getId(), e.getNombre(), e.getConferencia(), e.getDivision(),
                            stats.getVictorias(), stats.getDerrotas(),
                            stats.getPpg(), stats.getRpg(), stats.getApg());
                })
                .toList();
    }
}
