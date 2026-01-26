package controller.dashborad;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class DashboardController {

    @FXML
    private Label lblTotalPoints;
    
    @FXML
    private Label lblActiveBets;
    
    @FXML
    private Label lblWinRate;
    
    @FXML
    private Label lblRanking;
    
    @FXML
    private TableView<?> tableUpcomingMatches;
    
    @FXML
    private Pane chartContainer;
    
    @FXML
    private VBox root;

    /**
     * Navega a la vista de detalle de un partido.
     * @param matchId El ID del partido a mostrar
     */
    public void navigateToMatchDetail(int matchId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/partidos/detalle_partidos/MatchDetailView.fxml");
            // TODO: Pasar el matchId al controlador de detalle
        }
    }

    /**
     * Navega a la vista de detalle de un partido (sobrecarga sin parámetros).
     */
    @FXML
    private void navigateToMatchDetail() {
        navigateToMatchDetail(0); // Se puede obtener el ID del contexto
    }

    /**
     * Navega a la vista de lista de partidos.
     */
    @FXML
    private void navigateToMatches() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/partidos/lista_partidos/MatchesView.fxml");
        }
    }

    /**
     * Navega a la vista de apuestas.
     */
    @FXML
    private void navigateToBets() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/apuestas/BetsView.fxml");
        }
    }

    /**
     * Navega a la vista de rankings.
     */
    @FXML
    private void navigateToRankings() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/rankings/RankingsView.fxml");
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
