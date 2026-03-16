package com.tfg.nbabackend.controller;

import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.UsuarioRepository;
import com.tfg.nbabackend.service.ApuestaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que gestiona las operaciones relacionadas con apuestas.
 * 
 * <p>Proporciona endpoints para crear apuestas y consultar apuestas de usuarios.
 * Todos los endpoints están habilitados para CORS desde cualquier origen.
 * 
 * @author TFG
 * @version 1.0
 */
@RestController
@RequestMapping("/apuestas")
@CrossOrigin(origins = "*")
public class ApuestaController {

    private final ApuestaService apuestaService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor del controlador de apuestas.
     * 
     * @param apuestaService servicio de apuestas
     * @param usuarioRepository repositorio de usuarios
     */
    public ApuestaController(ApuestaService apuestaService,
                             UsuarioRepository usuarioRepository) {
        this.apuestaService = apuestaService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Crea una nueva apuesta en el sistema.
     * 
     * <p>Valida que el usuario tenga suficientes puntos, calcula la cuota
     * si no está especificada y descuenta los puntos del usuario.
     * 
     * @param apuesta la apuesta a crear con usuario, partido, predicción y puntos apostados
     * @return la apuesta creada
     * @throws RuntimeException si el usuario no tiene suficientes puntos o no existe
     */
    @PostMapping
    public Apuesta apostar(@RequestBody Apuesta apuesta) {
        return apuestaService.crearApuesta(apuesta);
    }

    /**
     * Obtiene todas las apuestas realizadas por un usuario.
     * 
     * @param id el ID del usuario
     * @return lista de apuestas del usuario
     * @throws RuntimeException si el usuario no existe
     */
    @GetMapping("/usuario/{id}")
    public List<Apuesta> apuestasUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        return apuestaService.obtenerPorUsuario(usuario);
    }
}