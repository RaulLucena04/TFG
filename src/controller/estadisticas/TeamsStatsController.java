package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class TeamsStatsController {

    @FXML
    private TableView<?> tableTeams;
    
    @FXML
    private VBox root;

    /**
     * Navega a la vista de detalle de un equipo.
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
     * Navega a la vista de detalle de un equipo (sobrecarga sin parámetros).
     */
    @FXML
    private void navigateToTeamDetail() {
        navigateToTeamDetail(0);
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
