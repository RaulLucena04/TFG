package controller.partidos.detalle_partidos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class MatchDetailController {

    @FXML
    private Label lblMatchDate;
    
    @FXML
    private Label lblMatchStatus;
    
    @FXML
    private Label lblHomeTeam;
    
    @FXML
    private Label lblHomeScore;
    
    @FXML
    private Label lblAwayTeam;
    
    @FXML
    private Label lblAwayScore;
    
    @FXML
    private TableView<?> tableStats;
    
    @FXML
    private VBox root;

    /**
     * Navega de vuelta a la vista anterior (lista de partidos).
     */
    @FXML
    private void handleBack() {
        navigateToMatches();
    }

    /**
     * Navega a la vista de lista de partidos.
     */
    public void navigateToMatches() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/partidos/lista_partidos/MatchesView.fxml");
        }
    }

    /**
     * Navega a la vista de crear apuesta para este partido.
     */
    @FXML
    private void handleCreateBet() {
        navigateToCreateBet();
    }

    /**
     * Navega a la vista de crear apuesta.
     */
    public void navigateToCreateBet() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/partidos/crear_apuesta/CreateBetView.fxml");
            // TODO: Pasar el ID del partido actual al controlador de crear apuesta
        }
    }

    /**
     * Navega a la vista de estadísticas detalladas del partido.
     */
    @FXML
    private void handleViewStats() {
        // TODO: Implementar vista de estadísticas detalladas o navegar a estadísticas generales
        navigateToStatistics();
    }

    /**
     * Navega a la vista de estadísticas.
     */
    public void navigateToStatistics() {
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
