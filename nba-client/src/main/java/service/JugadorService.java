package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Jugador;
import util.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class JugadorService {

    private static String getUrl() {
        return Config.getServerUrl() + "/jugadores";
    }

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Jugador> getAllPlayers() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getUrl()))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(),
                new TypeReference<List<Jugador>>() {});
    }
}