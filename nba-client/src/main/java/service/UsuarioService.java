package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class UsuarioService {

    private static final String BASE_URL = "http://localhost:8080/usuarios";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public User login(String username, String password) {
        try {
            URL url = new URL(BASE_URL + "/login");
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
    String url = BASE_URL + "/" + id + "/password";

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

}


