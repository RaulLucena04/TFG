package controller.layout;

import java.io.File;
import java.io.IOException;
import java.net.URL;

// --- NUEVOS IMPORTS NECESARIOS PARA EL LOGOUT ---
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import model.User;

public class MainLayoutController {

    @FXML
    private StackPane contentPane;

    @FXML
    private Button btnAdmin;

    @FXML
    private Label lblUser, lblPoints;

    public void initialize() {
        loadDashboard();
    }

    public void setUser(User user) {
        if (user != null) {
            lblUser.setText(user.getUsername());
            lblPoints.setText("Puntos: " + user.getPoints());

            if (user.isAdmin()) {
                btnAdmin.setVisible(true);
                btnAdmin.setManaged(true);
            } else {
                btnAdmin.setVisible(false);
                btnAdmin.setManaged(false);
            }
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

    private void loadView(String fxml) {
        try {
            // NOTA: Usar "src" + fxml funciona en desarrollo, pero fallará al crear el JAR final.
            // Para el futuro, considera usar getClass().getResource()
            File file = new File("src" + fxml);
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
            Parent view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error cargando vista: " + fxml);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            System.out.println("Cerrando sesión...");

            // 1. Cargar el FXML del Login
            // Asegúrate de que la ruta sea correcta según tu estructura de carpetas
            File file = new File("src/ui/auth/login/LoginView.fxml"); 
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
            Parent root = loader.load();

            // 2. Obtener el escenario (Stage) actual usando cualquier nodo visible (ej. contentPane)
            Stage stage = (Stage) contentPane.getScene().getWindow();

            // 3. Crear la nueva escena y asignarla al escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            
            // Opcional: Centrar la ventana en la pantalla
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar LoginView.fxml. Revisa la ruta.");
            e.printStackTrace();
        }
    }
}