package com.tfg.nbabackend.service;

import com.tfg.nbabackend.dto.CanjearPuntosRequest;
import com.tfg.nbabackend.dto.CanjearPuntosResponse;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio de tienda: canje de puntos por dinero (simulación PayPal).
 * Tasa de cambio: 1000 puntos = 1€
 */
@Service
public class TiendaService {

    private static final double EUROS_POR_1000_PUNTOS = 1.0;
    private static final int MINIMO_PUNTOS = 1000;

    private final UsuarioRepository usuarioRepository;

    public TiendaService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public CanjearPuntosResponse canjearPuntos(CanjearPuntosRequest request) {
        if (request.getUsuarioId() == null) {
            return new CanjearPuntosResponse(false, "Usuario no válido", 0, 0);
        }
        if (request.getPuntos() < MINIMO_PUNTOS) {
            return new CanjearPuntosResponse(false,
                "Mínimo " + MINIMO_PUNTOS + " puntos para canjear", 0, 0);
        }
        if (request.getEmailPayPal() == null || request.getEmailPayPal().isBlank()) {
            return new CanjearPuntosResponse(false, "Indica tu email de PayPal", 0, 0);
        }

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElse(null);
        if (usuario == null) {
            return new CanjearPuntosResponse(false, "Usuario no encontrado", 0, 0);
        }

        if (usuario.getPuntos() < request.getPuntos()) {
            return new CanjearPuntosResponse(false,
                "No tienes suficientes puntos. Disponibles: " + usuario.getPuntos(), 0, 0);
        }

        // Simular transferencia PayPal
        double euros = (request.getPuntos() / 1000.0) * EUROS_POR_1000_PUNTOS;

        usuario.setPuntos(usuario.getPuntos() - request.getPuntos());
        usuarioRepository.save(usuario);

        return new CanjearPuntosResponse(true,
            "Transferencia simulada a PayPal (" + request.getEmailPayPal() + "). " +
            "En producción se conectaría con la API de PayPal.",
            Math.round(euros * 100.0) / 100.0,
            request.getPuntos());
    }
}
