package com.tfg.nbabackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.nbabackend.enums.ResultadoApuesta;
import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.ApuestaRepository;
import com.tfg.nbabackend.repository.UsuarioRepository;

@Service
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final UsuarioRepository usuarioRepository;

    public ApuestaService(ApuestaRepository apuestaRepository,
            UsuarioRepository usuarioRepository) {
        this.apuestaRepository = apuestaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Apuesta crearApuesta(Apuesta apuesta) {

        Usuario usuario = usuarioRepository.findById(
                apuesta.getUsuario().getId()
        ).orElseThrow();

        if (usuario.getPuntos() < apuesta.getPuntosApostados()) {
            throw new RuntimeException("No tienes suficientes puntos");
        }

        // Restar puntos
        usuario.setPuntos(usuario.getPuntos() - apuesta.getPuntosApostados());
        usuarioRepository.save(usuario);

        // Estado inicial
        apuesta.setResultado(ResultadoApuesta.PENDIENTE);

        return apuestaRepository.save(apuesta);
    }

    public List<Apuesta> obtenerPorUsuario(Usuario usuario) {
        return apuestaRepository.findByUsuario(usuario);
    }

    public void resolverApuestas(Partido partido) {

        List<Apuesta> apuestas = apuestaRepository.findByPartido(partido);

        for (Apuesta apuesta : apuestas) {

            // ⚠️ Evitar resolver dos veces
            if (apuesta.getResultado() != ResultadoApuesta.PENDIENTE) {
                continue;
            }

            boolean ganaLocal = partido.getPuntosLocal() > partido.getPuntosVisitante();

            boolean acertada = false;

            if (ganaLocal && apuesta.getPrediccion().equalsIgnoreCase("LOCAL")) {
                acertada = true;
            }

            if (!ganaLocal && apuesta.getPrediccion().equalsIgnoreCase("VISITANTE")) {
                acertada = true;
            }

            if (acertada) {

                apuesta.setResultado(ResultadoApuesta.GANADA);

                Usuario usuario = apuesta.getUsuario();

                // Devuelve apuesta + ganancia (ejemplo x2)
                int ganancia = apuesta.getPuntosApostados() * 2;

                usuario.setPuntos(usuario.getPuntos() + ganancia);

                usuarioRepository.save(usuario);

            } else {
                apuesta.setResultado(ResultadoApuesta.PERDIDA);
            }

            apuestaRepository.save(apuesta);
        }
    }
}
