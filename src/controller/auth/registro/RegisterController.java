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

/**
 * Controlador encargado de gestionar el registro de nuevos usuarios.
 * Maneja la validación básica y la navegación entre vistas de autenticación.
 */
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

    /**
     * Gestiona el proceso de registro del usuario.
     * En este método se integrará la lógica de validación y persistencia.
     */
    @FXML
    private void handleRegister(ActionEvent event) {
        System.out.println("Registrando usuario: " + txtUsername.getText());
    }

    /**
     * Redirige al usuario a la pantalla de inicio de sesión.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/auth/login/LoginView.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            Scene scene = new Scene(root, 400, 500);
            scene.getStylesheets().add(
                getClass().getResource("/resources/styles/main.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Iniciar Sesión");
            stage.show();

        } catch (IOException e) {
            lblError.setText("Error cargando la pantalla de login.");
            lblError.setVisible(true);
            lblError.setManaged(true);
            e.printStackTrace();
        }
    }
}
