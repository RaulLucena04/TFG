package com.tfg.nbabackend.service;

import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.ApuestaRepository;
import com.tfg.nbabackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return apuestaRepository.save(apuesta);
    }

    public List<Apuesta> obtenerPorUsuario(Usuario usuario) {
        return apuestaRepository.findByUsuario(usuario);
    }

    public void resolverApuestas(Partido partido) {

        List<Apuesta> apuestas = apuestaRepository.findByPartido(partido);

        for (Apuesta apuesta : apuestas) {

            boolean ganaLocal = partido.getPuntosLocal() > partido.getPuntosVisitante();

            boolean acertada = false;

            if (ganaLocal && apuesta.getPrediccion().equalsIgnoreCase("LOCAL")) {
                acertada = true;
            }

            if (!ganaLocal && apuesta.getPrediccion().equalsIgnoreCase("VISITANTE")) {
                acertada = true;
            }

            apuesta.setAcertada(acertada);

            if (acertada) {
                Usuario usuario = apuesta.getUsuario();
                usuario.setPuntos(usuario.getPuntos() + apuesta.getPuntosApostados());
                usuarioRepository.save(usuario);
            }

            apuestaRepository.save(apuesta);
        }
    }
}