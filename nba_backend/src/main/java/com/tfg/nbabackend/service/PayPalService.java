package com.tfg.nbabackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

/**
 * Servicio para interactuar con la API de PayPal de desarrolladores.
 * Usa el entorno sandbox para pruebas.
 */
@Service
public class PayPalService {

    // URLs de la API de PayPal
    private static final String PAYPAL_SANDBOX_BASE = "https://api-m.sandbox.paypal.com";
    private static final String PAYPAL_PRODUCTION_BASE = "https://api-m.paypal.com";

    // Credenciales de PayPal (deben configurarse en application.properties)
    @Value("${paypal.client.id:}")
    private String clientId;

    @Value("${paypal.client.secret:}")
    private String clientSecret;

    @Value("${paypal.mode:sandbox}")
    private String paypalMode; // sandbox o production

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Obtiene un token de acceso de PayPal usando OAuth2
     */
    public String obtenerAccessToken() throws Exception {
        String baseUrl = "sandbox".equals(paypalMode) ? PAYPAL_SANDBOX_BASE : PAYPAL_PRODUCTION_BASE;
        String url = baseUrl + "/v1/oauth2/token";

        // Si no hay credenciales configuradas, usar modo simulación
        if (clientId == null || clientId.isEmpty() || clientSecret == null || clientSecret.isEmpty()) {
            return null; // Retornar null indica modo simulación
        }

        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Basic " + encodedCredentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            return jsonNode.get("access_token").asText();
        }

        throw new RuntimeException("Error al obtener token de PayPal: " + response.statusCode());
    }

    /**
     * Crea un pago Payout (transferencia) a través de PayPal
     * 
     * @param emailPayPal Email del destinatario
     * @param amount      Cantidad en euros
     * @return true si el pago fue exitoso
     */
    public boolean realizarPayout(String emailPayPal, double amount) {
        try {
            String accessToken = obtenerAccessToken();

            // Si no hay token (modo simulación), retornar true
            if (accessToken == null) {
                return true; // Modo simulación
            }

            String baseUrl = "sandbox".equals(paypalMode) ? PAYPAL_SANDBOX_BASE : PAYPAL_PRODUCTION_BASE;
            String url = baseUrl + "/v1/payments/payouts";

            // Crear el JSON para el payout
            String payoutJson = String.format(
                    java.util.Locale.US,
                    "{\n" +
                            "  \"sender_batch_header\": {\n" +
                            "    \"sender_batch_id\": \"batch_%d\",\n" +
                            "    \"email_subject\": \"Canje de puntos NBA Predictor\"\n" +
                            "  },\n" +
                            "  \"items\": [\n" +
                            "    {\n" +
                            "      \"recipient_type\": \"EMAIL\",\n" +
                            "      \"amount\": {\n" +
                            "        \"value\": \"%.2f\",\n" +
                            "        \"currency\": \"EUR\"\n" +
                            "      },\n" +
                            "      \"receiver\": \"%s\",\n" +
                            "      \"note\": \"Canje de puntos por dinero\",\n" +
                            "      \"sender_item_id\": \"item_%d\"\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}",
                    System.currentTimeMillis(),
                    amount,
                    emailPayPal,
                    System.currentTimeMillis());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payoutJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 🔍 DEBUG: imprimir respuesta completa de PayPal
            System.out.println("PayPal response code: " + response.statusCode());
            System.out.println("PayPal response body: " + response.body());

            return response.statusCode() == 201 || response.statusCode() == 202;

            // 201 Created para payouts

        } catch (Exception e) {
            // En caso de error, registrar y retornar false
            System.err.println("Error al realizar payout de PayPal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
