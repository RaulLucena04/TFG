package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Partido;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class PartidoService {

    private static final String BASE_URL = "http://localhost:8080/partidos";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<Partido> listarPartidos() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                Partido[] partidos = mapper.readValue(response.body(), Partido[].class);

                return Arrays.asList(partidos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }
}