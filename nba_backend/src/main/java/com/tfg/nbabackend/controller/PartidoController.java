package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.service.PartidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @GetMapping
    public List<Partido> listar() {
        return partidoService.listarPartidos();
    }

    @PostMapping
    public Partido crear(@RequestBody Partido partido) {
        return partidoService.guardar(partido);
    }

    @PutMapping("/{id}/finalizar")
    public Partido finalizar(@PathVariable Long id,
            @RequestParam Integer puntosLocal,
            @RequestParam Integer puntosVisitante) {

        return partidoService.finalizarPartido(id, puntosLocal, puntosVisitante);
    }

    @GetMapping("/equipo/{equipoId}")
    @CrossOrigin(origins = "*")
    public List<Partido> obtenerPartidosPorEquipo(@PathVariable Long equipoId) {
        // Filtramos los partidos donde el equipo es local o visitante
        return partidoService.listarPartidos().stream()
                .filter(p -> p.getEquipoLocal().getId().equals(equipoId)
                        || p.getEquipoVisitante().getId().equals(equipoId))
                .collect(Collectors.toList());
    }
}