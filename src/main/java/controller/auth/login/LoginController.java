package controller.auth.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblError;

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            // Carga la vista principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/layout/MainLayout.fxml"));
            Parent root = loader.load();

            // Obtiene el Stage desde el evento 
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crea una nueva escena y la pone en el stage
            Scene scene = new Scene(root, 1920, 1080);
            scene.getStylesheets().add(getClass().getResource("/resources/styles/main.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Principal");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            lblError.setText("Error cargando la aplicación.");
            lblError.setVisible(true);
            lblError.setManaged(true);
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth/registro/RegisterView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 400, 500); 
            scene.getStylesheets().add(getClass().getResource("/resources/styles/main.css").toExternalForm());

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Registro");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            lblError.setText("Error cargando el formulario de registro.");
            lblError.setVisible(true);
            lblError.setManaged(true);
        }
    }

}
