package controller.auth.registro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

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
    private void handleRegister(ActionEvent event) {
        // Aquí iría la lógica de validación y registro de usuario.
        // Por ahora, solo muestra un mensaje o puedes avanzar a otra pantalla.

        System.out.println("Registrando usuario: " + txtUsername.getText());
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth/login/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 400, 500);
            scene.getStylesheets().add(getClass().getResource("/resources/styles/main.css").toExternalForm());

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Iniciar Sesión");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            lblError.setText("Error cargando la pantalla de login.");
            lblError.setVisible(true);
            lblError.setManaged(true);
        }
    }
}
