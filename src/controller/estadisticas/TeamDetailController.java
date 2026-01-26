package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class TeamDetailController {

    @FXML
    private Label lblTeamName;
    
    @FXML
    private TableView<?> tablePlayers;
    
    @FXML
    private TableView<?> tableStats;
    
    @FXML
    private VBox root;

    /**
     * Navega de vuelta a la vista anterior (estadísticas de equipos).
     */
    @FXML
    private void handleBack() {
        navigateToTeamsStats();
    }

    /**
     * Navega a la vista de estadísticas de equipos.
     */
    public void navigateToTeamsStats() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/TeamsStatsView.fxml");
        }
    }

    /**
     * Navega a la vista de estadísticas generales.
     */
    public void navigateToStatistics() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/StatisticsView.fxml");
        }
    }

    /**
     * Navega a la vista de detalle de un jugador.
     * @param playerId El ID del jugador a mostrar
     */
    public void navigateToPlayerDetail(int playerId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/PlayerDetailView.fxml");
            // TODO: Pasar el playerId al controlador de detalle
        }
    }

    /**
     * Obtiene el StackPane del contenido principal desde MainLayoutController.
     */
    private StackPane getContentPane() {
        MainLayoutController mainController = MainLayoutController.getInstance();
        if (mainController != null) {
            return mainController.getContentPane();
        }
        return NavigationUtils.findContentPane(root);
    }
}
