package controller.apuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class BetDetailController {

    @FXML
    private Label lblBetId;
    
    @FXML
    private Label lblBetDate;
    
    @FXML
    private Label lblBetStatus;
    
    @FXML
    private Label lblBetType;
    
    @FXML
    private Label lblPrediction;
    
    @FXML
    private Label lblBetAmount;
    
    @FXML
    private Label lblOdds;
    
    @FXML
    private Label lblPotentialWin;
    
    @FXML
    private Label lblResult;
    
    @FXML
    private Label lblProfitLoss;
    
    @FXML
    private Label lblHomeTeam;
    
    @FXML
    private Label lblHomeScore;
    
    @FXML
    private Label lblAwayTeam;
    
    @FXML
    private Label lblAwayScore;
    
    @FXML
    private Label lblMatchDate;
    
    @FXML
    private VBox root;

    /**
     * Navega de vuelta a la vista anterior (lista de apuestas).
     */
    @FXML
    private void handleBack() {
        navigateToBets();
    }

    /**
     * Navega a la vista de lista de apuestas.
     */
    public void navigateToBets() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/apuestas/BetsView.fxml");
        }
    }

    /**
     * Navega a la vista de detalle del partido relacionado con esta apuesta.
     */
    public void navigateToMatchDetail(int matchId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/partidos/detalle_partidos/MatchDetailView.fxml");
            // TODO: Pasar el matchId al controlador de detalle
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
