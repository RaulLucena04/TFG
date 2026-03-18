package com.tfg.nbabackend.service;

import com.tfg.nbabackend.dto.EquipoEstadisticasDTO;
import com.tfg.nbabackend.enums.EstadoPartido;
import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.repository.PartidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio que calcula estadísticas de equipos.
 * 
 * <p>Este servicio calcula estadísticas basándose en:
 * <ul>
 *   <li>Record (victorias/derrotas) de partidos finalizados</li>
 *   <li>Promedios de jugadores (PPG, RPG, APG) sumando los promedios de todos los jugadores del equipo</li>
 * </ul>
 * 
 * @author TFG
 * @version 1.0
 */
@Service
public class EquipoEstadisticasService {

    private final PartidoRepository partidoRepository;
    private final JugadorService jugadorService;

    /**
     * Constructor del servicio de estadísticas de equipos.
     * 
     * @param partidoRepository repositorio de partidos
     * @param jugadorService servicio de jugadores
     */
    public EquipoEstadisticasService(PartidoRepository partidoRepository,
                                     JugadorService jugadorService) {
        this.partidoRepository = partidoRepository;
        this.jugadorService = jugadorService;
    }

    /**
     * Calcula estadísticas de un equipo.
     * 
     * <p>Calcula:
     * <ul>
     *   <li>Record: victorias y derrotas basadas en partidos finalizados donde participa el equipo</li>
     *   <li>PPG (Puntos Por Partido): suma de promedios de puntos de todos los jugadores</li>
     *   <li>RPG (Rebotes Por Partido): suma de promedios de rebotes de todos los jugadores</li>
     *   <li>APG (Asistencias Por Partido): suma de promedios de asistencias de todos los jugadores</li>
     * </ul>
     * 
     * @param equipoId el ID del equipo
     * @return DTO con las estadísticas calculadas del equipo
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
