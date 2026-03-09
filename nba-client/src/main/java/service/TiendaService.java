package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TiendaService {

    private static final String BASE_URL = "http://localhost:8080/tienda";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public CanjearPuntosResponse canjearPuntos(Long usuarioId, int puntos, String emailPayPal) throws Exception {
        var request = new CanjearPuntosRequest(usuarioId, puntos, emailPayPal);
        String json = mapper.writeValueAsString(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/canjear"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), CanjearPuntosResponse.class);
    }

    public static class CanjearPuntosRequest {
        public Long usuarioId;
        public int puntos;
        public String emailPayPal;

        public CanjearPuntosRequest() {}
        public CanjearPuntosRequest(Long usuarioId, int puntos, String emailPayPal) {
            this.usuarioId = usuarioId;
            this.puntos = puntos;
            this.emailPayPal = emailPayPal;
        }
    }

    public static class CanjearPuntosResponse {
        public boolean exito;
        public String mensaje;
        public double eurosTransferidos;
        public int puntosCanjeados;
    }
}
