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
import util.NavigationUtils; 

public class MainLayoutController {

    @FXML
    private StackPane contentPane;

    @FXML
    private Button btnAdmin;

    @FXML
    private Label lblUser, lblPoints;

    // Instancia estática para acceso desde otros controladores
    private static MainLayoutController instance;

    public MainLayoutController() {
        instance = this;
    }

    /**
     * Obtiene la instancia del MainLayoutController para acceso desde otros controladores.
     * @return La instancia del controlador
     */
    public static MainLayoutController getInstance() {
        return instance;
    }

    /**
     * Obtiene el StackPane del contenido principal.
     * @return El StackPane donde se cargan las vistas
     */
    public StackPane getContentPane() {
        return contentPane;
    }

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
        loadView("/ui/dashborad/DashboardView.fxml");
    }

    @FXML
    private void loadMatches() {
        loadView("/ui/partidos/lista_partidos/MatchesView.fxml");
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
        NavigationUtils.loadView(contentPane, fxml);
    }

    @FXML
    private void handleLogout() {
        // Aquí deberías añadir la lógica para cerrar sesión
        System.out.println("Cerrando sesión...");
        // Ejemplo: volver a cargar la escena del Login
    }
}