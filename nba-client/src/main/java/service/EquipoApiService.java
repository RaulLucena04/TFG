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

public class EquipoApiService {

    private static String getBaseUrl() {
        return Config.getServerUrl();
    }
    
    private static String getUrlEquipos() {
        return getBaseUrl() + "/equipos";
    }
    
    private static String getUrlJugadores() {
        return getBaseUrl() + "/jugadores/equipo/";
    }
    
    private static String getUrlPartidos() {
        return getBaseUrl() + "/partidos/equipo/";
    }

    private final ObjectMapper mapper;

    public EquipoApiService() {
        this.mapper = new ObjectMapper();
        // Registro para manejar LocalDateTime
        this.mapper.registerModule(new JavaTimeModule());
    }

    public List<Equipo> obtenerEquipos() throws Exception {
        URL url = new URL(getUrlEquipos());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Equipo[] equipos = mapper.readValue(input, Equipo[].class);
        return Arrays.asList(equipos);
    }

    public List<Equipo> obtenerEquiposConEstadisticas() throws Exception {
        URL url = new URL(getBaseUrl() + "/equipos-con-estadisticas");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Equipo[] equipos = mapper.readValue(input, Equipo[].class);
        return Arrays.asList(equipos);
    }

    public List<Jugador> obtenerJugadoresEquipo(Long equipoId) throws Exception {
        URL url = new URL(getUrlJugadores() + equipoId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Jugador[] jugadores = mapper.readValue(input, Jugador[].class);
        return Arrays.asList(jugadores);
    }

    public List<Partido> obtenerPartidosEquipo(Long equipoId) throws Exception {
        URL url = new URL(getUrlPartidos() + equipoId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Partido[] partidos = mapper.readValue(input, Partido[].class);
        return Arrays.asList(partidos);
    }

    public List<Jugador> obtenerTodosJugadores() throws Exception {
        URL url = new URL(getBaseUrl() + "/jugadores");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream input = connection.getInputStream();
        Jugador[] jugadores = mapper.readValue(input, Jugador[].class);
        return Arrays.asList(jugadores);
    }

    public model.EquipoEstadisticas obtenerEstadisticasEquipo(Long equipoId) throws Exception {
        URL url = new URL(getUrlEquipos() + "/" + equipoId + "/estadisticas");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream input = connection.getInputStream();
        return mapper.readValue(input, model.EquipoEstadisticas.class);
    }
}