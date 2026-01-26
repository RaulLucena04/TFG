package controller.partidos.lista_partidos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class MatchesController {

    @FXML
    private ComboBox<String> comboFilter;
    
    @FXML
    private VBox matchesContainer;

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
     * Útil para navegación desde botones.
     */
    @FXML
    private void navigateToMatchDetail() {
        navigateToMatchDetail(0); // Se puede obtener el ID del contexto
    }

    /**
     * Navega a la vista de crear apuesta para un partido específico.
     * @param matchId El ID del partido
     */
    public void navigateToCreateBet(int matchId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/partidos/crear_apuesta/CreateBetView.fxml");
            // TODO: Pasar el matchId al controlador de crear apuesta
        }
    }

    @FXML
    private void handleRefresh() {
        // TODO: Implementar lógica de actualización
        System.out.println("Actualizando partidos...");
    }

    /**
     * Obtiene el StackPane del contenido principal desde MainLayoutController.
     */
    private StackPane getContentPane() {
        MainLayoutController mainController = MainLayoutController.getInstance();
        if (mainController != null) {
            return mainController.getContentPane();
        }
        // Fallback: buscar en la jerarquía de nodos
        return NavigationUtils.findContentPane(matchesContainer);
    }
}
