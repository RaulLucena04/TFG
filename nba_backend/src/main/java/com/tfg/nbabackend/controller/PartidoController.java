package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.service.PartidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST que gestiona las operaciones relacionadas con partidos.
 * 
 * <p>Proporciona endpoints para listar, crear y finalizar partidos.
 * Todos los endpoints están habilitados para CORS desde cualquier origen.
 * 
 * @author TFG
 * @version 1.0
 */
@RestController
@RequestMapping("/partidos")
@CrossOrigin(origins = "*")
public class PartidoController {

    private final PartidoService partidoService;

    /**
     * Constructor del controlador de partidos.
     * 
     * @param partidoService servicio de partidos
     */
    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    /**
     * Obtiene todos los partidos del sistema.
     * 
     * @return lista de todos los partidos
     */
    @GetMapping
    public List<Partido> listar() {
        return partidoService.listarPartidos();
    }

    /**
     * Crea un nuevo partido en el sistema.
     * 
     * @param partido el partido a crear
     * @return el partido creado
     */
    @PostMapping
    public Partido crear(@RequestBody Partido partido) {
        return partidoService.guardar(partido);
    }

    /**
     * Finaliza un partido estableciendo el resultado.
     * 
     * <p>Al finalizar un partido, se resuelven automáticamente todas las apuestas
     * relacionadas con ese partido.
     * 
     * @param id el ID del partido a finalizar
     * @param puntosLocal los puntos del equipo local
     * @param puntosVisitante los puntos del equipo visitante
     * @return el partido finalizado
     */
    @PutMapping("/{id}/finalizar")
    public Partido finalizar(@PathVariable Long id,
            @RequestParam Integer puntosLocal,
            @RequestParam Integer puntosVisitante) {

        return partidoService.finalizarPartido(id, puntosLocal, puntosVisitante);
    }

    /**
     * Obtiene todos los partidos en los que participa un equipo específico.
     * 
     * <p>Incluye tanto los partidos donde el equipo juega como local
     * como los que juega como visitante.
     * 
     * @param equipoId el ID del equipo
     * @return lista de partidos del equipo
     */
    @GetMapping("/equipo/{equipoId}")
    public List<Partido> obtenerPartidosPorEquipo(@PathVariable Long equipoId) {
        return partidoService.listarPartidos().stream()
                .filter(p -> p.getEquipoLocal().getId().equals(equipoId)
                        || p.getEquipoVisitante().getId().equals(equipoId))
                .collect(Collectors.toList());
    }
}