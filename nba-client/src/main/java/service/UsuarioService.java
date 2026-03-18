package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import util.Config;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

/**
 * Servicio que gestiona las operaciones relacionadas con usuarios.
 * 
 * <p>Proporciona métodos para autenticación, actualización de contraseña,
 * consulta de usuarios y actualización de datos de usuario mediante
 * comunicación con la API REST del servidor.
 * 
 * @author TFG
 * @version 1.0
 */
public class UsuarioService {

    /**
     * Obtiene la URL base para operaciones de usuarios.
     * 
     * @return la URL base del endpoint de usuarios
     */
    private static String getBaseUrl() {
        return Config.getServerUrl() + "/usuarios";
    }

    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Autentica un usuario con username y contraseña.
     * 
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @return el usuario autenticado si las credenciales son correctas, null en caso contrario
     */
    public User login(String username, String password) {
        try {
            URL url = new URL(getBaseUrl() + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();

            User usuario = new User();
            usuario.setUsername(username);
            usuario.setPassword(password);

            String json = mapper.writeValueAsString(usuario);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                return mapper.readValue(response, User.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Actualiza la contraseña de un usuario.
     * 
     * @param id el ID del usuario
     * @param newPassword la nueva contraseña
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean updatePassword(Long id, String newPassword) {
        String url = getBaseUrl() + "/" + id + "/password";

        try {
            String json = "{ \"password\": \"" + newPassword + "\" }";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los usuarios del sistema ordenados por puntos descendente.
     * 
     * @return lista de usuarios ordenada por puntos (mayor a menor)
     */
    public java.util.List<User> listarUsuarios() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBaseUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                ObjectMapper mapper = new ObjectMapper();
                User[] usuarios = mapper.readValue(response.body(), User[].class);

                return java.util.Arrays.stream(usuarios)
                        .sorted(java.util.Comparator
                                .comparingInt(User::getPoints)
                                .reversed())
                        .toList();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return java.util.List.of();
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param usuario el usuario con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarUsuario(User usuario) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(usuario);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBaseUrl() + "/" + usuario.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene un usuario por su identificador.
     * 
     * @param id el ID del usuario
     * @return el usuario encontrado, o null si no existe o hay un error
     */
    public User obtenerUsuarioPorId(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBaseUrl() + "/" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body(), User.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
