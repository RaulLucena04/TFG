package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.service.EquipoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
@CrossOrigin(origins = "*") // permite llamadas desde JavaFX/Android
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
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

    // POST crear equipo (panel admin)
    @PostMapping
    public Equipo crearEquipo(@RequestBody Equipo equipo) {
        return equipoService.guardar(equipo);
    }
}