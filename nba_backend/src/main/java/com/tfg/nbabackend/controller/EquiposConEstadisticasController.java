package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.dto.EquipoConEstadisticasDTO;
import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.service.EquipoEstadisticasService;
import com.tfg.nbabackend.service.EquipoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoint alternativo para evitar conflicto de rutas con /equipos/{id}.
 * GET /equipos-con-estadisticas devuelve equipos con record, ppg, rpg, apg.
 */
@RestController
@RequestMapping("/equipos-con-estadisticas")
@CrossOrigin(origins = "*")
public class EquiposConEstadisticasController {

    private final EquipoService equipoService;
    private final EquipoEstadisticasService equipoEstadisticasService;

    public EquiposConEstadisticasController(EquipoService equipoService,
                                            EquipoEstadisticasService equipoEstadisticasService) {
        this.equipoService = equipoService;
        this.equipoEstadisticasService = equipoEstadisticasService;
    }

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
