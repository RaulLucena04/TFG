package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.service.EquipoService;
import com.tfg.nbabackend.service.JugadorService;
import com.tfg.nbabackend.service.PartidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {

    private final EquipoService equipoService;
    private final JugadorService jugadorService;
    private final PartidoService partidoService;

    public EquipoController(EquipoService equipoService,
                            JugadorService jugadorService,
                            PartidoService partidoService) {
        this.equipoService = equipoService;
        this.jugadorService = jugadorService;
        this.partidoService = partidoService;
    }

    // GET todos los equipos
    @GetMapping
    public List<Equipo> listarEquipos() {
        return equipoService.listarTodos();
    }

    // GET equipo por ID
    @GetMapping("/{id}")
    public Equipo obtenerEquipo(@PathVariable Long id) {
        return equipoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    // ✅ Obtener jugadores de un equipo
    @GetMapping("/{id}/jugadores")
    public List<Jugador> obtenerJugadores(@PathVariable Long id) {
        return jugadorService.obtenerPorEquipo(id);
    }

    // ✅ Obtener partidos de un equipo
    @GetMapping("/{id}/partidos")
    public List<Partido> obtenerPartidos(@PathVariable Long id) {
        return partidoService.listarPartidos().stream()
                .filter(p -> p.getEquipoLocal().getId().equals(id) ||
                             p.getEquipoVisitante().getId().equals(id))
                .toList();
    }

    // POST crear equipo
    @PostMapping
    public Equipo crearEquipo(@RequestBody Equipo equipo) {
        return equipoService.guardar(equipo);
    }
}