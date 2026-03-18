package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Equipo;
import model.Jugador;
import model.Partido;
import util.Config;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    /**
     * Obtiene la URL base del servidor.
     * 
     * @return la URL base del servidor
     */
    private static String getBaseUrl() {
        return Config.getServerUrl();
    }
    
    /**
     * Obtiene la URL del endpoint de equipos.
     * 
     * @return la URL del endpoint de equipos
     */
    private static String getUrlEquipos() {
        return getBaseUrl() + "/equipos";
    }
    
    /**
     * Obtiene la URL base del endpoint de jugadores por equipo.
     * 
     * @return la URL base del endpoint de jugadores
     */
    private static String getUrlJugadores() {
        return getBaseUrl() + "/jugadores/equipo/";
    }
    
    /**
     * Obtiene la URL base del endpoint de partidos por equipo.
     * 
     * @return la URL base del endpoint de partidos
     */
    private static String getUrlPartidos() {
        return getBaseUrl() + "/partidos/equipo/";
    }

    private final ObjectMapper mapper;

    /**
     * Constructor del servicio de equipos.
     * 
     * <p>Inicializa el ObjectMapper con soporte para LocalDateTime.
     */
    public EquipoApiService() {
        this.mapper = new ObjectMapper();
        // Registro para manejar LocalDateTime
        this.mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Obtiene todos los equipos del sistema.
     * 
     * @return lista de todos los equipos
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public List<Equipo> obtenerEquipos() throws Exception {
        URL url = new URL(getUrlEquipos());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Equipo[] equipos = mapper.readValue(input, Equipo[].class);
        return Arrays.asList(equipos);
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
        URL url = new URL(getBaseUrl() + "/equipos-con-estadisticas");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Equipo[] equipos = mapper.readValue(input, Equipo[].class);
        return Arrays.asList(equipos);
    }

    /**
     * Obtiene todos los jugadores de un equipo específico.
     * 
     * @param equipoId el ID del equipo
     * @return lista de jugadores del equipo
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public List<Jugador> obtenerJugadoresEquipo(Long equipoId) throws Exception {
        URL url = new URL(getUrlJugadores() + equipoId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Jugador[] jugadores = mapper.readValue(input, Jugador[].class);
        return Arrays.asList(jugadores);
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
        URL url = new URL(getUrlPartidos() + equipoId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Partido[] partidos = mapper.readValue(input, Partido[].class);
        return Arrays.asList(partidos);
    }

    /**
     * Obtiene todos los jugadores del sistema.
     * 
     * @return lista de todos los jugadores
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public List<Jugador> obtenerTodosJugadores() throws Exception {
        URL url = new URL(getBaseUrl() + "/jugadores");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream input = connection.getInputStream();
        Jugador[] jugadores = mapper.readValue(input, Jugador[].class);
        return Arrays.asList(jugadores);
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
        URL url = new URL(getUrlEquipos() + "/" + equipoId + "/estadisticas");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream input = connection.getInputStream();
        return mapper.readValue(input, model.EquipoEstadisticas.class);
    }
}