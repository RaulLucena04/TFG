package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class MatchManagementController {

    @FXML
    private TableView<?> tableMatches;
    
    @FXML
    private VBox root;

    /**
     * Navega de vuelta al panel de administración.
     */
    @FXML
    private void handleBack() {
        navigateToAdminDashboard();
    }

    /**
     * Navega al panel de administración.
     */
    public void navigateToAdminDashboard() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/admin/AdminDashboardView.fxml");
        }
    }

    /**
     * Navega al formulario de partido para crear uno nuevo.
     */
    @FXML
    private void handleCreateMatch() {
        navigateToMatchForm();
    }

    /**
     * Navega al formulario de partido para editar uno existente.
     * @param matchId El ID del partido a editar
     */
    public void navigateToMatchForm(int matchId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/admin/MatchFormView.fxml");
            // TODO: Pasar el matchId al controlador del formulario
        }
    }

    /**
     * Navega al formulario de partido para crear uno nuevo.
     */
    public void navigateToMatchForm() {
        navigateToMatchForm(0);
    }

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

    @FXML
    private void handleRefresh() {
        // TODO: Implementar lógica de actualización
        System.out.println("Actualizando gestión de partidos...");
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
