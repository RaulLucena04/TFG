package controller.perfil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class ProfileController {

    @FXML
    private Label lblUsername;
    
    @FXML
    private Label lblPoints;
    
    @FXML
    private TableView<?> tableBetHistory;
    
    @FXML
    private VBox root;

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
     * Navega a la vista de detalle de una apuesta.
     * @param betId El ID de la apuesta a mostrar
     */
    public void navigateToBetDetail(int betId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/apuestas/BetDetailView.fxml");
            // TODO: Pasar el betId al controlador de detalle
        }
    }

    @FXML
    private void handleSave() {
        // TODO: Implementar lógica de guardado de perfil
        System.out.println("Guardando perfil...");
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
