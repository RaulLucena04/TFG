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
            // Prueba varias rutas posibles según cómo esté configurado el classpath
            java.net.URL fxmlUrl = getClass().getResource("/ui/layout/MainLayout.fxml");
            if (fxmlUrl == null) {
                fxmlUrl = getClass().getResource("/main/resources/ui/layout/MainLayout.fxml");
            }
            if (fxmlUrl == null) {
                fxmlUrl = getClass().getClassLoader().getResource("ui/layout/MainLayout.fxml");
            }
            if (fxmlUrl == null) {
                throw new IllegalStateException("No se encontró MainLayout.fxml");
            }

            // Carga la vista principal
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            // Obtiene el Stage desde el evento 
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crea una nueva escena y la pone en el stage
            Scene scene = new Scene(root, 1920, 1080);
            
            // Carga el CSS con múltiples rutas posibles
            java.net.URL cssUrl = getClass().getResource("/styles/main.css");
            if (cssUrl == null) {
                cssUrl = getClass().getResource("/styles/main.css");
            }
            if (cssUrl == null) {
                cssUrl = getClass().getClassLoader().getResource("styles/main.css");
            }
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Principal");
            stage.show();

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            lblError.setText("Error cargando la aplicación: " + e.getMessage());
            lblError.setVisible(true);
            lblError.setManaged(true);
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            // Prueba varias rutas posibles
            java.net.URL fxmlUrl = getClass().getResource("/ui/auth/registro/RegisterView.fxml");
            if (fxmlUrl == null) {
                fxmlUrl = getClass().getResource("/main/resources/ui/auth/registro/RegisterView.fxml");
            }
            if (fxmlUrl == null) {
                fxmlUrl = getClass().getClassLoader().getResource("ui/auth/registro/RegisterView.fxml");
            }
            if (fxmlUrl == null) {
                throw new IllegalStateException("No se encontró RegisterView.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 400, 500);
            
            // Carga el CSS con múltiples rutas posibles
            java.net.URL cssUrl = getClass().getResource("/styles/main.css");
            if (cssUrl == null) {
                cssUrl = getClass().getResource("/styles/main.css");
            }
            if (cssUrl == null) {
                cssUrl = getClass().getClassLoader().getResource("styles/main.css");
            }
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Registro");
            stage.show();

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            lblError.setText("Error cargando el formulario de registro: " + e.getMessage());
            lblError.setVisible(true);
            lblError.setManaged(true);
        }
    }

}
