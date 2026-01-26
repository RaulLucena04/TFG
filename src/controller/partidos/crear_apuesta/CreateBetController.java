package controller.partidos.crear_apuesta;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class CreateBetController {

    @FXML
    private Label lblMatchInfo;
    
    @FXML
    private Label lblMatchDate;
    
    @FXML
    private ToggleGroup betTypeGroup;
    
    @FXML
    private RadioButton radioWinner;
    
    @FXML
    private RadioButton radioPointDiff;
    
    @FXML
    private RadioButton radioTotalPoints;
    
    @FXML
    private ComboBox<String> comboWinner;
    
    @FXML
    private TextField txtBetAmount;
    
    @FXML
    private Label lblAvailablePoints;
    
    @FXML
    private Slider sliderBetAmount;
    
    @FXML
    private Label lblOdds;
    
    @FXML
    private Label lblPotentialWin;
    
    @FXML
    private Label lblError;
    
    @FXML
    private VBox root;

    /**
     * Cancela la creación de apuesta y vuelve a la vista anterior.
     */
    @FXML
    private void handleCancel() {
        navigateBack();
    }

    /**
     * Confirma la creación de la apuesta.
     */
    @FXML
    private void handleConfirmBet() {
        // TODO: Implementar lógica de confirmación de apuesta
        System.out.println("Confirmando apuesta...");
        // Después de confirmar, podría navegar a la vista de apuestas o al detalle de la apuesta creada
        // navigateToBets();
    }

    /**
     * Navega de vuelta a la vista anterior.
     */
    public void navigateBack() {
        // Por defecto, vuelve a la lista de partidos
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
     * Navega a la vista de detalle del partido.
     */
    public void navigateToMatchDetail(int matchId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/partidos/detalle_partidos/MatchDetailView.fxml");
            // TODO: Pasar el matchId al controlador de detalle
        }
    }

    /**
     * Navega a la vista de apuestas.
     */
    public void navigateToBets() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/apuestas/BetsView.fxml");
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
