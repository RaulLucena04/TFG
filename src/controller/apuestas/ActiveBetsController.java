package controller.apuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class ActiveBetsController {

    @FXML
    private ComboBox<String> comboFilter;
    
    @FXML
    private TableView<?> tableActiveBets;
    
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
        navigateToBetDetail(0);
    }

    /**
     * Navega de vuelta a la vista de apuestas.
     */
    @FXML
    private void navigateToBets() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/apuestas/BetsView.fxml");
        }
    }

    @FXML
    private void handleRefresh() {
        // TODO: Implementar lógica de actualización
        System.out.println("Actualizando apuestas activas...");
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
