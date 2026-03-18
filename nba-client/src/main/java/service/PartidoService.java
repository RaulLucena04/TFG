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

public class PartidoService {

    private static String getBaseUrl() {
        return Config.getServerUrl() + "/partidos";
    }
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper;

    public PartidoService() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

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