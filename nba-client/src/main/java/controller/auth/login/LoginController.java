package controller.auth.login;

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
import model.User;
import service.UsuarioService;
import session.Session;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblError;

    @FXML
    public void initialize() {
        txtUsername.setOnAction(this::handleLogin);
        txtPassword.setOnAction(this::handleLogin);
    }

    @FXML
    private void handleLogin(ActionEvent event) {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Rellena todos los campos");
            lblError.setVisible(true);
            return;
        }

        UsuarioService usuarioService = new UsuarioService();
        User usuario = usuarioService.login(username, password);

        if (usuario == null) {
            lblError.setText("Usuario o contraseña incorrectos");
            lblError.setVisible(true);
            return;
        }

        Session.setCurrentUser(usuario);

        try {
            java.net.URL fxmlUrl = getClass().getResource("/ui/layout/MainLayout.fxml");

            if (fxmlUrl == null) {
                throw new IllegalStateException("No se encontró MainLayout.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            controller.layout.MainLayoutController controller = loader.getController();
            controller.setUser(usuario);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);

            java.net.URL cssUrl = getClass().getResource("/styles/main.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setScene(scene);
            stage.sizeToScene();
            stage.setWidth(1400);
            stage.setHeight(850);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Principal");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Error cargando la pantalla principal");
            lblError.setVisible(true);
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            java.net.URL fxmlUrl = getClass().getResource("/ui/auth/registro/RegisterView.fxml");
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

            java.net.URL cssUrl = getClass().getResource("/styles/main.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Registro");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Error cargando el formulario de registro");
            lblError.setVisible(true);
        }
    }
}
