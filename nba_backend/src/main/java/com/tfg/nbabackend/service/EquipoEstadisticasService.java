package com.tfg.nbabackend.service;

import com.tfg.nbabackend.dto.EquipoEstadisticasDTO;
import com.tfg.nbabackend.enums.EstadoPartido;
import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.repository.PartidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoEstadisticasService {

    private final PartidoRepository partidoRepository;
    private final JugadorService jugadorService;

    public EquipoEstadisticasService(PartidoRepository partidoRepository,
                                     JugadorService jugadorService) {
        this.partidoRepository = partidoRepository;
        this.jugadorService = jugadorService;
    }

    /**
     * Calcula estadísticas de un equipo: record (de partidos finalizados) y PPG/RPG/APG (suma de jugadores).
     */
    public EquipoEstadisticasDTO calcularEstadisticas(Long equipoId) {
        List<Partido> todos = partidoRepository.findAll();
        int victorias = 0, derrotas = 0;
        for (Partido p : todos) {
            if (p.getEstado() != EstadoPartido.FINALIZADO) continue;
            if (p.getEquipoLocal() == null || p.getEquipoVisitante() == null) continue;
            if (!p.getEquipoLocal().getId().equals(equipoId) && !p.getEquipoVisitante().getId().equals(equipoId))
                continue;

            boolean esLocal = p.getEquipoLocal().getId().equals(equipoId);
            int puntosEquipo = esLocal ? (p.getPuntosLocal() != null ? p.getPuntosLocal() : 0)
                    : (p.getPuntosVisitante() != null ? p.getPuntosVisitante() : 0);
            int puntosRival = esLocal ? (p.getPuntosVisitante() != null ? p.getPuntosVisitante() : 0)
                    : (p.getPuntosLocal() != null ? p.getPuntosLocal() : 0);
            if (puntosEquipo > puntosRival) victorias++;
            else derrotas++;
        }

        List<Jugador> jugadores = jugadorService.obtenerPorEquipo(equipoId);
        double ppg = 0, rpg = 0, apg = 0;
        for (Jugador j : jugadores) {
            ppg += (j.getPromedioPuntos() != null ? j.getPromedioPuntos() : 0);
            rpg += (j.getPromedioRebotes() != null ? j.getPromedioRebotes() : 0);
            apg += (j.getPromedioAsistencias() != null ? j.getPromedioAsistencias() : 0);
        }

        return new EquipoEstadisticasDTO(victorias, derrotas, ppg, rpg, apg);
    }
}
