package controller.auth.registro;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label lblError;

    @FXML
    public void initialize() {
        txtUsername.setOnAction(this::handleRegister);
        txtEmail.setOnAction(this::handleRegister);
        txtPassword.setOnAction(this::handleRegister);
        txtConfirmPassword.setOnAction(this::handleRegister);
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblError.setText("Todos los campos son obligatorios");
            lblError.setVisible(true);
            lblError.setManaged(true);
            return;
        }

        if (!password.equals(confirmPassword)) {
            lblError.setText("Las contraseñas no coinciden");
            lblError.setVisible(true);
            lblError.setManaged(true);
            return;
        }

        try {
            String json = String.format(
                    "{\"username\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}",
                    username, email, password);

            java.net.URL url = new java.net.URL("http://localhost:8080/usuarios/register");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (java.io.OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == 200 || responseCode == 201) {
                handleLogin(event);
            } else {
                lblError.setText("Error al registrar usuario");
                lblError.setVisible(true);
                lblError.setManaged(true);
            }

        } catch (Exception e) {
            lblError.setText("Error de conexión con el servidor");
            lblError.setVisible(true);
            lblError.setManaged(true);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            java.net.URL fxmlUrl = getClass().getResource("/ui/auth/login/LoginView.fxml");
            if (fxmlUrl == null) {
                fxmlUrl = getClass().getClassLoader().getResource("ui/auth/login/LoginView.fxml");
            }
            if (fxmlUrl == null) {
                throw new IllegalStateException("No se encontró LoginView.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 400, 500);

            java.net.URL cssUrl = getClass().getResource("/styles/main.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Iniciar Sesión");
            stage.show();

        } catch (IOException | IllegalStateException e) {
            lblError.setText("Error cargando la pantalla de login: " + e.getMessage());
            lblError.setVisible(true);
            lblError.setManaged(true);
            e.printStackTrace();
        }
    }
}
