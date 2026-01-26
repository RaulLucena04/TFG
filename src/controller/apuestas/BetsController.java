package controller.apuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class BetsController {

    @FXML
    private TabPane tabPane;
    
    @FXML
    private ComboBox<String> comboFilterActive;
    
    @FXML
    private ComboBox<String> comboFilterHistory;
    
    @FXML
    private TableView<?> tableActiveBets;
    
    @FXML
    private TableView<?> tableBetHistory;
    
    @FXML
    private VBox root;

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

    /**
     * Navega a la vista de detalle de una apuesta (sobrecarga sin parámetros).
     */
    @FXML
    private void navigateToBetDetail() {
        navigateToBetDetail(0); // Se puede obtener el ID del contexto
    }

    /**
     * Navega a la vista de crear apuesta.
     */
    @FXML
    private void navigateToCreateBet() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/partidos/crear_apuesta/CreateBetView.fxml");
        }
    }

    /**
     * Navega a la vista de apuestas activas.
     */
    @FXML
    private void navigateToActiveBets() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/apuestas/ActiveBetsView.fxml");
        }
    }

    /**
     * Navega a la vista de historial de apuestas.
     */
    @FXML
    private void navigateToBetHistory() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/apuestas/BetHistoryView.fxml");
        }
    }

    @FXML
    private void handleRefreshActive() {
        // TODO: Implementar lógica de actualización de apuestas activas
        System.out.println("Actualizando apuestas activas...");
    }

    @FXML
    private void handleRefreshHistory() {
        // TODO: Implementar lógica de actualización de historial
        System.out.println("Actualizando historial de apuestas...");
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
