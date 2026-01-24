package controller.layout;

// --- AÑADE ESTOS IMPORTS ---
import java.io.IOException;

// Imports de JavaFX necesarios para los controles y anotaciones
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

// Import de tu clase modelo (VERIFICA ESTA LÍNEA)
// Si tu clase User está en "package model;", esto funcionará.
// Si está en otro paquete, cambia "model" por el nombre correcto.
import model.User; 

public class MainLayoutController {

    @FXML
    private StackPane contentPane;

    @FXML
    private Button btnAdmin;

    @FXML
    private Label lblUser, lblPoints;

    public void initialize() {
        // Asegúrate de que este FXML existe, si no dará error al arrancar
        loadDashboard(); 
    }

    public void setUser(User user) {
        // Verifica que el objeto user no sea null para evitar NullPointerException
        if (user != null) {
            lblUser.setText(user.getUsername());
            lblPoints.setText("Puntos: " + user.getPoints());

            if (user.isAdmin()) {
                btnAdmin.setVisible(true);
                btnAdmin.setManaged(true);
            } else {
                // Es buena práctica ocultarlo si no es admin
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
            // Carga el archivo FXML
            Parent view = FXMLLoader.load(getClass().getResource(fxml));
            // Limpia el panel y añade la nueva vista
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error cargando la vista: " + fxml);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("No se encontró el archivo FXML: " + fxml);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        // Aquí deberías añadir la lógica para cerrar sesión
        System.out.println("Cerrando sesión...");
        // Ejemplo: volver a cargar la escena del Login
    }
}