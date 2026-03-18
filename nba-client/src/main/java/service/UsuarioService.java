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

public class UsuarioService {

    private static String getBaseUrl() {
        return Config.getServerUrl() + "/usuarios";
    }

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public User login(String username, String password) {
        try {
            URL url = new URL(getBaseUrl() + "/login");
            String finalUrl = getBaseUrl() + "/login";
               System.out.println("URL FINAL = " + finalUrl);  // <-- AQUI
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
