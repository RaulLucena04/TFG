package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Equipo;
import model.Jugador;
import model.Partido;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class EquipoApiService {

    private static final String URL_API_EQUIPOS = "http://localhost:8080/equipos";
    private static final String URL_API_JUGADORES = "http://localhost:8080/jugadores/equipo/";
    private static final String URL_API_PARTIDOS = "http://localhost:8080/partidos/equipo/";

    private final ObjectMapper mapper;

    public EquipoApiService() {
        this.mapper = new ObjectMapper();
        // Registro para manejar LocalDateTime
        this.mapper.registerModule(new JavaTimeModule());
    }

    public List<Equipo> obtenerEquipos() throws Exception {
        URL url = new URL(URL_API_EQUIPOS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Equipo[] equipos = mapper.readValue(input, Equipo[].class);
        return Arrays.asList(equipos);
    }

    public List<Jugador> obtenerJugadoresEquipo(Long equipoId) throws Exception {
        URL url = new URL(URL_API_JUGADORES + equipoId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Jugador[] jugadores = mapper.readValue(input, Jugador[].class);
        return Arrays.asList(jugadores);
    }

    public List<Partido> obtenerPartidosEquipo(Long equipoId) throws Exception {
        URL url = new URL(URL_API_PARTIDOS + equipoId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream input = connection.getInputStream();
        Partido[] partidos = mapper.readValue(input, Partido[].class);
        return Arrays.asList(partidos);
    }
}