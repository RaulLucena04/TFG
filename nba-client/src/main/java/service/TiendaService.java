package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import util.Config;

/**
 * Servicio que gestiona las operaciones relacionadas con la tienda virtual.
 * 
 * <p>Proporciona métodos para canjear puntos virtuales por dinero mediante
 * integración con PayPal API. Tasa de cambio: 1000 puntos = 1€.
 * 
 * @author TFG
 * @version 1.0
 */
public class TiendaService {

    /**
     * Obtiene la URL base para operaciones de tienda.
     * 
     * @return la URL base del endpoint de tienda
     */
    private static String getBaseUrl() {
        return Config.getServerUrl() + "/tienda";
    }
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Canjea puntos virtuales por dinero mediante transferencia PayPal.
     * 
     * <p>Valida que el usuario tenga suficientes puntos (mínimo 1000), calcula
     * la cantidad en euros y realiza la transferencia a través de PayPal API.
     * 
     * @param usuarioId el ID del usuario que canjea los puntos
     * @param puntos la cantidad de puntos a canjear (mínimo 1000)
     * @param emailPayPal el email de PayPal del destinatario
     * @return respuesta con el resultado del canje (éxito/error, mensaje, euros transferidos)
     * @throws Exception si hay un error al procesar el pago con PayPal
     */
    public CanjearPuntosResponse canjearPuntos(Long usuarioId, int puntos, String emailPayPal) throws Exception {
        var request = new CanjearPuntosRequest(usuarioId, puntos, emailPayPal);
        String json = mapper.writeValueAsString(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/canjear"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), CanjearPuntosResponse.class);
    }

    /**
     * Clase que representa una solicitud de canje de puntos.
     */
    public static class CanjearPuntosRequest {
        /** ID del usuario que canjea los puntos */
        public Long usuarioId;
        /** Cantidad de puntos a canjear */
        public int puntos;
        /** Email de PayPal del destinatario */
        public String emailPayPal;

        /**
         * Constructor por defecto.
         */
        public CanjearPuntosRequest() {}
        
        /**
         * Constructor con parámetros.
         * 
         * @param usuarioId ID del usuario
         * @param puntos cantidad de puntos
         * @param emailPayPal email de PayPal
         */
        public CanjearPuntosRequest(Long usuarioId, int puntos, String emailPayPal) {
            this.usuarioId = usuarioId;
            this.puntos = puntos;
            this.emailPayPal = emailPayPal;
        }
    }

    /**
     * Clase que representa la respuesta de un canje de puntos.
     */
    public static class CanjearPuntosResponse {
        /** Indica si el canje fue exitoso */
        public boolean exito;
        /** Mensaje descriptivo del resultado */
        public String mensaje;
        /** Cantidad en euros transferida */
        public double eurosTransferidos;
        /** Cantidad de puntos canjeados */
        public int puntosCanjeados;
    }
}
