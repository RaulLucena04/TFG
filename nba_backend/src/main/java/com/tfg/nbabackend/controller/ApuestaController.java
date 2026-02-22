package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.UsuarioRepository;
import com.tfg.nbabackend.service.ApuestaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apuestas")
public class ApuestaController {

    private final ApuestaService apuestaService;
    private final UsuarioRepository usuarioRepository;

    public ApuestaController(ApuestaService apuestaService,
                             UsuarioRepository usuarioRepository) {
        this.apuestaService = apuestaService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public Apuesta apostar(@RequestBody Apuesta apuesta) {
        return apuestaService.crearApuesta(apuesta);
    }

    @GetMapping("/usuario/{id}")
    public List<Apuesta> apuestasUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        return apuestaService.obtenerPorUsuario(usuario);
    }
}