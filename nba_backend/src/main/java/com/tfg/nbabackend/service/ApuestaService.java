package com.tfg.nbabackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.nbabackend.enums.ResultadoApuesta;
import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.ApuestaRepository;
import com.tfg.nbabackend.repository.PartidoRepository;
import com.tfg.nbabackend.repository.UsuarioRepository;

@Service
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PartidoRepository partidoRepository;
    private final EquipoEstadisticasService equipoEstadisticasService;

    public ApuestaService(ApuestaRepository apuestaRepository,
            UsuarioRepository usuarioRepository,
            PartidoRepository partidoRepository,
            EquipoEstadisticasService equipoEstadisticasService) {
        this.apuestaRepository = apuestaRepository;
        this.usuarioRepository = usuarioRepository;
        this.partidoRepository = partidoRepository;
        this.equipoEstadisticasService = equipoEstadisticasService;
    }

    public Apuesta crearApuesta(Apuesta apuesta) {

        Usuario usuario = usuarioRepository.findById(
                apuesta.getUsuario().getId()
        ).orElseThrow();

        if (usuario.getPuntos() < apuesta.getPuntosApostados()) {
            throw new RuntimeException("No tienes suficientes puntos");
        }

        // Calcular cuota si no viene en la apuesta
        if (apuesta.getCuota() == null || apuesta.getCuota() <= 0) {
            Partido partidoCompleto = apuesta.getPartido() != null && apuesta.getPartido().getId() != null
                    ? partidoRepository.findById(apuesta.getPartido().getId()).orElse(null)
                    : null;
            double cuota = calcularCuota(partidoCompleto, apuesta.getPrediccion());
            apuesta.setCuota(cuota);
        }

        // Restar puntos
        usuario.setPuntos(usuario.getPuntos() - apuesta.getPuntosApostados());
        usuarioRepository.save(usuario);

        // Estado inicial
        apuesta.setResultado(ResultadoApuesta.PENDIENTE);

        return apuestaRepository.save(apuesta);
    }

    /**
     * Calcula la cuota según record (victorias/derrotas), ventaja local y predicción.
     * - Más victorias = favorito = cuota más baja al apostar por él
     * - Local tiene ventaja inherente (cuota algo más baja)
     * - Cuota base 2.0, rango 1.5 - 5.0
     */
    private double calcularCuota(Partido partido, String prediccion) {
        if (partido == null || partido.getEquipoLocal() == null || partido.getEquipoVisitante() == null) {
            return 2.0;
        }

        Long idLocal = partido.getEquipoLocal().getId();
        Long idVisitante = partido.getEquipoVisitante().getId();

        var statsLocal = equipoEstadisticasService.calcularEstadisticas(idLocal);
        var statsVisitante = equipoEstadisticasService.calcularEstadisticas(idVisitante);

        int vLocal = statsLocal.getVictorias();
        int dLocal = statsLocal.getDerrotas();
        int vVisitante = statsVisitante.getVictorias();
        int dVisitante = statsVisitante.getDerrotas();

        int totalLocal = vLocal + dLocal;
        int totalVisitante = vVisitante + dVisitante;
        double winRateLocal = totalLocal > 0 ? (double) vLocal / totalLocal : 0.5;
        double winRateVisitante = totalVisitante > 0 ? (double) vVisitante / totalVisitante : 0.5;

        // Diferencia de record: positivo si local es mejor
        double diferenciaRecord = winRateLocal - winRateVisitante;

        // Ventaja de jugar en casa (aprox. +5% probabilidad)
        final double VENTAJA_LOCAL = 0.05;

        double cuotaBase = 2.0;

        if ("LOCAL".equalsIgnoreCase(prediccion)) {
            // Apostar por local: si local es favorito, cuota baja; si visitante es mejor, cuota alta
            // diferenciaRecord > 0 → local mejor → cuota más baja
            double factor = -diferenciaRecord * 0.6 - VENTAJA_LOCAL;
            cuotaBase = 2.0 + factor;
        } else {
            // Apostar por visitante: si visitante es favorito, cuota baja
            double factor = diferenciaRecord * 0.6 + VENTAJA_LOCAL;
            cuotaBase = 2.0 + factor;
        }

        double cuota = Math.max(1.5, Math.min(5.0, cuotaBase));
        return Math.round(cuota * 100.0) / 100.0;
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

                // Calcular ganancia basada en la cuota
                double cuota = apuesta.getCuota() != null ? apuesta.getCuota() : 2.0;
                int ganancia = (int) Math.round(apuesta.getPuntosApostados() * cuota);

                usuario.setPuntos(usuario.getPuntos() + ganancia);

                usuarioRepository.save(usuario);

            } else {
                apuesta.setResultado(ResultadoApuesta.PERDIDA);
            }

            apuestaRepository.save(apuesta);
        }
    }
}
