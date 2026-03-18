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

/**
 * Servicio que gestiona las operaciones relacionadas con apuestas.
 * 
 * <p>Proporciona métodos para crear apuestas y obtener apuestas de usuarios
 * mediante comunicación con la API REST del servidor.
 * 
 * @author TFG
 * @version 1.0
 */
public class ApuestaService {

    /**
     * Obtiene la URL base para operaciones de apuestas.
     * 
     * @return la URL base del endpoint de apuestas
     */
    private static String getBaseUrl() {
        return Config.getServerUrl() + "/apuestas";
    }
    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Obtiene todas las apuestas realizadas por un usuario.
     * 
     * @param userId el ID del usuario
     * @return lista de apuestas del usuario, lista vacía si hay un error
     */
    public List<Apuesta> obtenerApuestasUsuario(Long userId) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBaseUrl() + "/usuario/" + userId))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                ObjectMapper mapper = new ObjectMapper();
                // Registrar módulo para manejar LocalDateTime de Java 8+
                mapper.registerModule(new JavaTimeModule());

                Apuesta[] apuestas = mapper.readValue(response.body(), Apuesta[].class);

                return Arrays.asList(apuestas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }

    /**
     * Crea una nueva apuesta en el sistema.
     * 
     * <p>Envía la apuesta al servidor que valida que el usuario tenga suficientes
     * puntos, calcula la cuota si no está especificada y descuenta los puntos.
     * 
     * @param apuesta la apuesta a crear con usuario, partido, predicción y puntos apostados
     * @throws RuntimeException si el usuario no tiene suficientes puntos, el partido no existe
     *                          o hay un error de conexión
     */
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