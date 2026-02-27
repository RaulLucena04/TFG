package controller.perfil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth/password/ChangePasswordView.fxml"));
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("Cambiar Contraseña");
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);
            dialog.initOwner(lblUsername.getScene().getWindow());
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
