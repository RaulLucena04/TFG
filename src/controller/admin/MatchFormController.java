package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class MatchFormController {

    @FXML
    private TextField txtHomeTeam;
    
    @FXML
    private TextField txtAwayTeam;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private VBox root;

    /**
     * Cancela la operación y vuelve a la gestión de partidos.
     */
    @FXML
    private void handleCancel() {
        navigateToMatchManagement();
    }

    /**
     * Guarda el partido (crear o actualizar).
     */
    @FXML
    private void handleSave() {
        // TODO: Implementar lógica de guardado
        System.out.println("Guardando partido...");
        // Después de guardar, volver a la gestión de partidos
        // navigateToMatchManagement();
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
     * Navega al panel de administración.
     */
    public void navigateToAdminDashboard() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/admin/AdminDashboardView.fxml");
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
