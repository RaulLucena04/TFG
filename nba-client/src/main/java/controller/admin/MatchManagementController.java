package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MatchManagementController {

    @FXML
    private TextField txtSearch;

    @FXML
    private ComboBox<String> comboStatusFilter;

    @FXML
    private DatePicker dateFilter;

    @FXML
    private TableView<?> tableMatches;

    @FXML
    private Label lblSelectedCount;

    @FXML
    private void initialize() {
        comboStatusFilter.getItems().addAll(
            "Todos",
            "Programados",
            "En curso",
            "Finalizados"
        );

        comboStatusFilter.setValue("Todos");
    }

    @FXML
    private void handleBack() {
        // navegación futura
    }

    @FXML
    private void handleSearch() {
        // búsqueda futura
    }

    @FXML
    private void handleCreateMatch() {
        // abrir MatchFormView
    }

    @FXML
    private void handleRefresh() {
        // refrescar tabla
    }

    @FXML
    private void handleEditSelected() {
        // editar partido
    }

    @FXML
    private void handleDeleteSelected() {
        // eliminar partido
    }

    @FXML
    private void handleFinishSelected() {
        // finalizar partido
    }
}
