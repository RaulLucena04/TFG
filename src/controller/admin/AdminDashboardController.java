package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class AdminDashboardController {

    @FXML
    private Label lblTotalUsers;
    
    @FXML
    private Label lblActiveMatches;
    
    @FXML
    private Label lblTotalBets;
    
    @FXML
    private Label lblTotalTeams;
    
    @FXML
    private TableView<?> tablePendingMatches;
    
    @FXML
    private TableView<?> tableRecentActivity;
    
    @FXML
    private VBox root;

    /**
     * Navega a la vista de gestión de partidos.
     */
    @FXML
    private void handleManageMatches() {
        navigateToMatchManagement();
    }

    /**
     * Navega a la vista de gestión de usuarios.
     */
    @FXML
    private void handleManageUsers() {
        navigateToUserManagement();
    }

    /**
     * Navega a la vista de crear partido.
     */
    @FXML
    private void handleCreateMatch() {
        navigateToMatchForm();
    }

    /**
     * Navega a la vista de gestión de partidos.
     */
    public void navigateToMatchManagement() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/admin/MatchManagementView.fxml");
        }
    }

    /**
     * Navega a la vista de gestión de usuarios.
     */
    public void navigateToUserManagement() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/admin/UserManagementView.fxml");
        }
    }

    /**
     * Navega a la vista de formulario de partido (crear/editar).
     */
    public void navigateToMatchForm() {
        navigateToMatchForm(null);
    }

    /**
     * Navega a la vista de formulario de partido para editar un partido existente.
     * @param matchId El ID del partido a editar (null para crear nuevo)
     */
    public void navigateToMatchForm(Integer matchId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/admin/MatchFormView.fxml");
            // TODO: Pasar el matchId al controlador del formulario
        }
    }

    @FXML
    private void handleRefreshData() {
        // TODO: Implementar lógica de actualización de datos
        System.out.println("Actualizando datos del panel de administración...");
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
