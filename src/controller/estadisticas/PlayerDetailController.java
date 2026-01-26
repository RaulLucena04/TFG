package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class PlayerDetailController {

    @FXML
    private Label lblPlayerName;
    
    @FXML
    private Label lblTeamName;
    
    @FXML
    private TableView<?> tableStats;
    
    @FXML
    private VBox root;

    /**
     * Navega de vuelta a la vista anterior (estadísticas de jugadores).
     */
    @FXML
    private void handleBack() {
        navigateToPlayersStats();
    }

    /**
     * Navega a la vista de estadísticas de jugadores.
     */
    public void navigateToPlayersStats() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/PlayersStatsView.fxml");
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
     * Navega a la vista de detalle del equipo del jugador.
     * @param teamId El ID del equipo a mostrar
     */
    public void navigateToTeamDetail(int teamId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/TeamDetailView.fxml");
            // TODO: Pasar el teamId al controlador de detalle
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
