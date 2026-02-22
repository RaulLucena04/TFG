package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.service.JugadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jugadores")
@CrossOrigin(origins = "*")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    // GET todos los jugadores
    @GetMapping
    public List<Jugador> listarJugadores() {
        return jugadorService.listarTodos();
    }

    // GET jugadores por equipo
    @GetMapping("/equipo/{equipoId}")
    public List<Jugador> jugadoresPorEquipo(@PathVariable Long equipoId) {
        return jugadorService.obtenerPorEquipo(equipoId);
    }

    // POST crear jugador (panel admin)
    @PostMapping
    public Jugador crearJugador(@RequestBody Jugador jugador) {
        return jugadorService.guardar(jugador);
    }
}