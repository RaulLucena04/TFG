package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Apuesta;

public class ApuestaService {

    private static final String BASE_URL = "http://localhost:8080/apuestas";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<Apuesta> obtenerApuestasUsuario(Long userId) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/usuario/" + userId))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule()); // 🔥 SOLUCIÓN

                Apuesta[] apuestas = mapper.readValue(response.body(), Apuesta[].class);

                return Arrays.asList(apuestas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }
}