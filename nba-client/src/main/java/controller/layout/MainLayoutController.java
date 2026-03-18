package controller.layout;

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
import service.UsuarioService;
import session.Session;

/**
 * Controlador principal del layout de la aplicación.
 * 
 * <p>Gestiona la navegación entre diferentes vistas y mantiene la información
 * del usuario en la barra superior (nombre de usuario y puntos). Proporciona
 * métodos para actualizar los puntos del usuario cuando se realizan cambios
 * (apuestas, canjes, etc.).
 * 
 * <p>Implementa el patrón Singleton para permitir acceso desde otros controladores
 * y actualizar los puntos del layout cuando sea necesario.
 * 
 * @author TFG
 * @version 1.0
 */
public class MainLayoutController {

    private static MainLayoutController instance;

    @FXML
    private StackPane contentPane;

    @FXML
    private Button btnAdmin;

    @FXML
    private Label lblUser, lblPoints;

    private final UsuarioService usuarioService = new UsuarioService();

    /**
     * Constructor del controlador principal.
     * 
     * <p>Establece esta instancia como la instancia singleton del controlador.
     */
    public MainLayoutController() {
        instance = this;
    }

    /**
     * Obtiene la instancia singleton del controlador principal.
     * 
     * <p>Permite a otros controladores acceder al MainLayoutController para
     * actualizar los puntos del usuario en el layout.
     * 
     * @return la instancia del controlador principal, o null si no se ha inicializado
     */
    public static MainLayoutController getInstance() {
        return instance;
    }

    /**
     * Inicializa el controlador después de cargar el FXML.
     * 
     * <p>Carga el dashboard por defecto y establece la información del usuario
     * en la barra superior.
     */
    public void initialize() {
        // Cargar dashboard
        loadDashboard();

        // Cargar usuario desde sesión
        User user = Session.getCurrentUser();
        setUser(user);
    }

    /**
     * Actualiza los puntos del usuario obteniéndolos desde el servidor
     * y actualiza la UI del layout.
     * 
     * <p>Este método debe llamarse cada vez que se hace un cambio en los puntos
     * (apuesta, canje, resolución de apuestas, etc.) para mantener la información
     * sincronizada con el servidor y actualizar la interfaz de usuario.
     * 
     * <p>Obtiene el usuario actualizado desde el servidor, actualiza la sesión
     * y refresca la información mostrada en la barra superior del layout.
     */
    public void actualizarPuntos() {
        User currentUser = Session.getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) {
            return;
        }

        try {
            // Obtener usuario actualizado desde el servidor
            User updatedUser = usuarioService.obtenerUsuarioPorId(currentUser.getId());
            
            if (updatedUser != null) {
                // Actualizar sesión
                Session.setCurrentUser(updatedUser);
                
                // Actualizar UI
                setUser(updatedUser);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar puntos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Establece la información del usuario en la interfaz.
     * 
     * <p>Actualiza el nombre de usuario y los puntos en la barra superior,
     * y muestra/oculta el botón de administración según el rol del usuario.
     * 
     * @param user el usuario a mostrar en la interfaz
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

    public void loadMatches() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/partidos/MatchesView.fxml"));
            Parent view = loader.load();

            contentPane.getChildren().setAll(view); // donde contentPane es tu contenedor central
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/estadisticas/StatisticsView.fxml"));
            Parent view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void loadTienda() {
        loadView("/ui/tienda/TiendaView.fxml");
    }

    @FXML
    private void loadAdmin() {
        loadView("/ui/admin/AdminDashboardView.fxml");
    }

    private void loadView(String fxml) {
        try {
            java.net.URL fxmlUrl = getClass().getResource(fxml);
            if (fxmlUrl == null) {
                fxmlUrl = getClass().getClassLoader().getResource(fxml.substring(1));
            }
            if (fxmlUrl == null) {
                System.err.println("No se encontró el archivo FXML: " + fxml);
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent view = loader.load();

            view.setOpacity(0);
            contentPane.getChildren().setAll(view);

            javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(200),
                    view);
            fade.setToValue(1);
            fade.play();

        } catch (IOException e) {
            System.err.println("Error cargando vista: " + fxml);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Session.clear();

            java.net.URL fxmlUrl = getClass().getResource("/ui/auth/login/LoginView.fxml");
            if (fxmlUrl == null) {
                fxmlUrl = getClass().getClassLoader().getResource("ui/auth/login/LoginView.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Scene scene = new Scene(root, 400, 500);

            java.net.URL cssUrl = getClass().getResource("/styles/main.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("NBA Predictor - Login");
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de Login.");
            e.printStackTrace();
        }
    }

}
