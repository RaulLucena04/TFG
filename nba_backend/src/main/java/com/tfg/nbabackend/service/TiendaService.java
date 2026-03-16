package com.tfg.nbabackend.service;

import org.springframework.stereotype.Service;

import com.tfg.nbabackend.dto.CanjearPuntosRequest;
import com.tfg.nbabackend.dto.CanjearPuntosResponse;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.UsuarioRepository;

/**
 * Servicio de tienda: canje de puntos por dinero usando PayPal API.
 * Tasa de cambio: 1000 puntos = 1€
 */
@Service
public class TiendaService {

    private static final double EUROS_POR_1000_PUNTOS = 1.0;
    private static final int MINIMO_PUNTOS = 1000;

    private final UsuarioRepository usuarioRepository;
    private final PayPalService payPalService;

    public TiendaService(UsuarioRepository usuarioRepository, PayPalService payPalService) {
        this.usuarioRepository = usuarioRepository;
        this.payPalService = payPalService;
    }

    public CanjearPuntosResponse canjearPuntos(CanjearPuntosRequest request) throws Exception {
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

        // Calcular cantidad en euros
        double euros = (request.getPuntos() / 1000.0) * EUROS_POR_1000_PUNTOS;
        euros = Math.round(euros * 100.0) / 100.0;

        // Realizar transferencia a través de PayPal API
        boolean pagoExitoso = payPalService.realizarPayout(request.getEmailPayPal(), euros);

        if (!pagoExitoso) {
            return new CanjearPuntosResponse(false,
                "Error al procesar el pago a través de PayPal. Por favor, intenta de nuevo más tarde.",
                0, 0);
        }

        // Restar puntos del usuario
        usuario.setPuntos(usuario.getPuntos() - request.getPuntos());
        usuarioRepository.save(usuario);

        String mensaje = payPalService.obtenerAccessToken() != null
            ? "Transferencia realizada exitosamente a PayPal (" + request.getEmailPayPal() + ")."
            : "Transferencia procesada (modo simulación - configura credenciales de PayPal para usar la API real).";

        return new CanjearPuntosResponse(true, mensaje, euros, request.getPuntos());
    }
}
