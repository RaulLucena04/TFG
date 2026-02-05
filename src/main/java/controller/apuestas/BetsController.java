package controller.apuestas;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;

public class BetsController {

    @FXML
    private TabPane tabPane;

    @FXML
    private ComboBox<String> comboFilterActive;

    @FXML
    private ComboBox<String> comboFilterHistory;

    @FXML
    private TableView<?> tableActiveBets;

    @FXML
    private TableView<?> tableBetHistory;

    @FXML
    private Label lblTotalBet;

    @FXML
    private Label lblTotalWins;

    @FXML
    private Label lblWinRate;

    @FXML
    private void initialize() {
        // Inicialización del controlador
        if (comboFilterActive != null) {
            comboFilterActive.getSelectionModel().selectFirst();
        }
        if (comboFilterHistory != null) {
            comboFilterHistory.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void handleRefreshActive() {
        // Lógica para actualizar apuestas activas
        System.out.println("Actualizando apuestas activas...");
        // TODO: Implementar lógica de actualización
    }

    @FXML
    private void handleRefreshHistory() {
        // Lógica para actualizar historial de apuestas
        System.out.println("Actualizando historial de apuestas...");
        // TODO: Implementar lógica de actualización
    }
}
