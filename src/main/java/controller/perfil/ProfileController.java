package controller.perfil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class ProfileController {

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblRegistrationDate;

    @FXML
    private Label lblTotalPoints;

    @FXML
    private Label lblTotalBets;

    @FXML
    private Label lblWinRate;

    @FXML
    private Label lblRanking;

    @FXML
    private Pane chartContainer;

    @FXML
    private TableView<?> tableRecentBets;

    @FXML
    private void initialize() {
        // Inicialización del controlador
        // TODO: Cargar datos del usuario actual
    }

    @FXML
    private void handleEditProfile() {
        // Lógica para editar el perfil
        System.out.println("Editando perfil...");
        // TODO: Implementar lógica de edición de perfil
        // Por ejemplo, abrir un diálogo o cambiar a una vista de edición
    }

    @FXML
    private void handleChangePassword() {
        // Lógica para cambiar la contraseña
        System.out.println("Cambiando contraseña...");
        // TODO: Implementar lógica de cambio de contraseña
        // Por ejemplo, abrir un diálogo de cambio de contraseña
    }
}
