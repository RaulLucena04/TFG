package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class PlayersStatsController {

    @FXML
    private TableView<?> tablePlayers;
    
    @FXML
    private VBox root;

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
     * Navega a la vista de detalle de un jugador (sobrecarga sin parámetros).
     */
    @FXML
    private void navigateToPlayerDetail() {
        navigateToPlayerDetail(0);
    }

    /**
     * Navega de vuelta a la vista de estadísticas.
     */
    @FXML
    private void navigateToStatistics() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/StatisticsView.fxml");
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
