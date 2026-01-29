package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserManagementController {

    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> comboRoleFilter;
    @FXML private ComboBox<String> comboStatusFilter;
    @FXML private TableView<?> tableUsers;

    @FXML private Label lblSelectedCount;
    @FXML private Label lblTotalUsers;
    @FXML private Label lblActiveUsers;
    @FXML private Label lblAdmins;

    @FXML
    private void initialize() {
        comboRoleFilter.getItems().addAll(
            "Todos",
            "Usuario",
            "Administrador"
        );
        comboRoleFilter.setValue("Todos");

        comboStatusFilter.getItems().addAll(
            "Todos",
            "Activo",
            "Inactivo",
            "Baneado"
        );
        comboStatusFilter.setValue("Todos");
    }

    @FXML private void handleBack() {}
    @FXML private void handleSearch() {}
    @FXML private void handleRefresh() {}
    @FXML private void handleEditSelected() {}
    @FXML private void handleChangeRole() {}
    @FXML private void handleDeactivateSelected() {}
    @FXML private void handleActivateSelected() {}
}
