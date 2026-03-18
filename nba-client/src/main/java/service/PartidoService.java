package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Partido;
import util.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con partidos.
 * 
 * <p>Proporciona métodos para listar, crear y finalizar partidos mediante
 * comunicación con la API REST del servidor.
 * 
 * @author TFG
 * @version 1.0
 */
public class PartidoService {

    /**
     * Obtiene la URL base para operaciones de partidos.
     * 
     * @return la URL base del endpoint de partidos
     */
    private static String getBaseUrl() {
        return Config.getServerUrl() + "/partidos";
    }
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper;

    /**
     * Constructor del servicio de partidos.
     * 
     * <p>Inicializa el ObjectMapper con soporte para LocalDateTime.
     */
    public PartidoService() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Obtiene todos los partidos del sistema.
     * 
     * @return lista de todos los partidos, lista vacía si hay un error
     */
    public List<Partido> listarPartidos() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBaseUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Partido[] partidos = mapper.readValue(response.body(), Partido[].class);
                return Arrays.asList(partidos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }

    /**
     * Crea un nuevo partido en el sistema.
     * 
     * <p>Principalmente usado desde el panel de administración.
     * 
     * @param partido el partido a crear
     * @return el partido creado, o null si hay un error
     */
    public Partido crearPartido(Partido partido) {
        try {
            String json = mapper.writeValueAsString(partido);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBaseUrl()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return mapper.readValue(response.body(), Partido.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finaliza un partido estableciendo el resultado.
     * 
     * <p>Al finalizar un partido, el servidor resuelve automáticamente
     * todas las apuestas relacionadas con ese partido.
     * 
     * @param id el ID del partido a finalizar
     * @param puntosLocal los puntos del equipo local
     * @param puntosVisitante los puntos del equipo visitante
     * @return true si la finalización fue exitosa, false en caso contrario
     */
    public boolean finalizarPartido(Long id, Integer puntosLocal, Integer puntosVisitante) {
        try {
            String url = getBaseUrl() + "/" + id + "/finalizar?puntosLocal=" + puntosLocal + "&puntosVisitante=" + puntosVisitante;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}