package controller.layout;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import model.User;

/**
 * Controlador principal del layout de la aplicación.
 * Gestiona la navegación entre vistas y la información del usuario activo.
 */
public class MainLayoutController {

    @FXML
    private StackPane contentPane;

    @FXML
    private Button btnAdmin;

    @FXML
    private Label lblUser, lblPoints;

    /**
     * Inicializa la vista principal cargando el panel de inicio.
     */
    public void initialize() {
        loadDashboard();
    }

    /**
     * Establece la información del usuario autenticado y ajusta
     * la interfaz según su rol.
     */
    public void setUser(User user) {
        if (user != null) {
            lblUser.setText(user.getUsername());
            lblPoints.setText("Puntos: " + user.getPoints());

            btnAdmin.setVisible(user.isAdmin());
            btnAdmin.setManaged(user.isAdmin());
        }
    }

    @FXML
    private void loadDashboard() {
        loadView("/ui/dashboard/DashboardView.fxml");
    }

    @FXML
    private void loadMatches() {
        loadView("/ui/partidos/MatchesView.fxml");
    }

    @FXML
    private void loadStatistics() {
        loadView("/ui/estadisticas/StatisticsView.fxml");
    }

    @FXML
    private void loadBets() {
        loadView("/ui/apuestas/BetsView.fxml");
    }

    @FXML
    private void loadRankings() {
        loadView("/ui/rankings/RankingsView.fxml");
    }

    @FXML
    private void loadProfile() {
        loadView("/ui/perfil/ProfileView.fxml");
    }

    @FXML
    private void loadAdmin() {
        loadView("/ui/admin/AdminDashboardView.fxml");
    }

    /**
     * Carga una vista FXML dentro del contenedor principal.
     */
    private void loadView(String fxml) {
        try {
            File file = new File("src" + fxml);
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
            Parent view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error cargando vista: " + fxml);
            e.printStackTrace();
        }
    }

    /**
     * Finaliza la sesión actual y redirige al usuario a la pantalla de login.
     */
    @FXML
    private void handleLogout() {
        try {
            File fxmlFile = new File("src/ui/auth/login/LoginView.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();

            Scene scene = new Scene(root);

            File cssFile = new File("src/resources/styles/main.css");
            if (cssFile.exists()) {
                scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());
            }

            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de Login.");
            e.printStackTrace();
        }
    }
}
