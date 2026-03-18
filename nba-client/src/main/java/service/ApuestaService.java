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
import util.Config;

public class ApuestaService {

    private static String getBaseUrl() {
        return Config.getServerUrl() + "/apuestas";
    }
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<Apuesta> obtenerApuestasUsuario(Long userId) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBaseUrl() + "/usuario/" + userId))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

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

    public void crearApuesta(Apuesta apuesta) throws RuntimeException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            String json = mapper.writeValueAsString(apuesta);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBaseUrl()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200 && response.statusCode() != 201) {
                String errorMessage = response.body();
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    throw new RuntimeException(errorMessage);
                } else {
                    throw new RuntimeException("Error al crear apuesta. Código: " + response.statusCode());
                }
            }

        } catch (RuntimeException e) {
            // Re-lanzar RuntimeException para que el controlador pueda manejarla
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error de conexión al crear apuesta: " + e.getMessage());
        }
    }
}