package service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Servicio que proporciona métodos para comunicarse con la API REST del backend.
 * 
 * <p>Este servicio maneja las peticiones HTTP al servidor backend para
 * realizar operaciones como login, registro y otras funcionalidades.
 * 
 * @author TFG
 * @version 1.0
 */
public class ApiService {

    /**
     * URL base del servidor backend para operaciones de usuarios.
     */
    private static final String BASE_URL = "http://localhost:8080/usuarios";

    /**
     * Autentica un usuario en el sistema enviando credenciales al servidor.
     * 
     * <p>Envía una petición POST al endpoint de login con el username y password
     * del usuario, y retorna la respuesta del servidor en formato JSON.
     * 
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @return la respuesta del servidor en formato JSON con los datos del usuario
     * @throws Exception si hay un error en la comunicación con el servidor
     */
    public static String login(String username, String password) throws Exception {

        URL url = new URL(BASE_URL + "/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInput = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password
        );

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes());
            os.flush();
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        conn.disconnect();

        return response.toString();
    }
}