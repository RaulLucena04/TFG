package controller.rankings;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class RankingsController {

    @FXML
    private TableView<?> tableRankings;
    
    @FXML
    private VBox root;

    /**
     * Navega a la vista de perfil de un usuario.
     * @param userId El ID del usuario a mostrar
     */
    public void navigateToProfile(int userId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/perfil/ProfileView.fxml");
            // TODO: Pasar el userId al controlador de perfil
        }
    }

    /**
     * Navega a la vista de perfil de un usuario (sobrecarga sin parámetros).
     */
    @FXML
    private void navigateToProfile() {
        navigateToProfile(0);
    }

    @FXML
    private void handleRefresh() {
        // TODO: Implementar lógica de actualización
        System.out.println("Actualizando rankings...");
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
