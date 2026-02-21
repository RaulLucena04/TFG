package service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {

    private static final String BASE_URL = "http://localhost:8080/usuarios";

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