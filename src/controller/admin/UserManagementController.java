package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class UserManagementController {

    @FXML
    private TableView<?> tableUsers;
    
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

    @FXML
    private void handleRefresh() {
        // TODO: Implementar lógica de actualización
        System.out.println("Actualizando gestión de usuarios...");
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
