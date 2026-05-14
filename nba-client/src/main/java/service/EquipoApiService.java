package service;

import model.Equipo;
import model.Jugador;
import model.Partido;
import java.util.Arrays;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con equipos, jugadores y estadísticas.
 * 
 * <p>Proporciona métodos para obtener equipos, jugadores, partidos y estadísticas
 * mediante comunicación con la API REST del servidor.
 * 
 * @author TFG
 * @version 1.0
 */
public class EquipoApiService {
    private final SocketApiClient api = new SocketApiClient();

    /**
     * Constructor del servicio de equipos.
     * 
     * <p>Inicializa el ObjectMapper con soporte para LocalDateTime.
     */
    public EquipoApiService() {
    }

    /**
     * Obtiene todos los equipos del sistema.
     * 
     * @return lista de todos los equipos
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public List<Equipo> obtenerEquipos() throws Exception {
        var equipos = api.request("team.list", java.util.Map.of(),
                new com.fasterxml.jackson.core.type.TypeReference<List<Equipo>>() {});
        return equipos;
    }

    /**
     * Obtiene todos los equipos con sus estadísticas calculadas.
     * 
     * <p>Incluye record (victorias/derrotas) y promedios de jugadores (PPG, RPG, APG).
     * 
     * @return lista de equipos con estadísticas
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public List<Equipo> obtenerEquiposConEstadisticas() throws Exception {
        // El backend devuelve DTO (EquipoConEstadisticasDTO). En el cliente usamos el modelo Equipo
        // existente, que no incluye victorias/derrotas/ppg/rpg/apg; aquí se mantiene el método por compatibilidad
        // y se mapea al modelo Equipo (campos comunes).
        var equipos = api.request("team.withStats", java.util.Map.of(),
                new com.fasterxml.jackson.core.type.TypeReference<List<Equipo>>() {});
        return equipos;
    }

    /**
     * Obtiene todos los jugadores de un equipo específico.
     * 
     * @param equipoId el ID del equipo
     * @return lista de jugadores del equipo
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public List<Jugador> obtenerJugadoresEquipo(Long equipoId) throws Exception {
        return api.request("player.byTeam",
                java.util.Map.of("equipoId", equipoId),
                new com.fasterxml.jackson.core.type.TypeReference<List<Jugador>>() {});
    }

    /**
     * Obtiene todos los partidos en los que participa un equipo.
     * 
     * <p>Incluye tanto los partidos donde el equipo juega como local
     * como los que juega como visitante.
     * 
     * @param equipoId el ID del equipo
     * @return lista de partidos del equipo
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public List<Partido> obtenerPartidosEquipo(Long equipoId) throws Exception {
        return api.request("match.byTeam",
                java.util.Map.of("equipoId", equipoId),
                new com.fasterxml.jackson.core.type.TypeReference<List<Partido>>() {});
    }

    /**
     * Obtiene todos los jugadores del sistema.
     * 
     * @return lista de todos los jugadores
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public List<Jugador> obtenerTodosJugadores() throws Exception {
        return api.request("player.list", java.util.Map.of(),
                new com.fasterxml.jackson.core.type.TypeReference<List<Jugador>>() {});
    }

    /**
     * Obtiene las estadísticas calculadas de un equipo.
     * 
     * <p>Incluye record (victorias/derrotas) y promedios de jugadores (PPG, RPG, APG).
     * 
     * @param equipoId el ID del equipo
     * @return estadísticas del equipo
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public model.EquipoEstadisticas obtenerEstadisticasEquipo(Long equipoId) throws Exception {
        return api.request("team.stats",
                java.util.Map.of("equipoId", equipoId),
                model.EquipoEstadisticas.class);
    }
}