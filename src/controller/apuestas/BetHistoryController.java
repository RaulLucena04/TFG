package controller.apuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class BetHistoryController {

    @FXML
    private Button btnBack;

    @FXML
    private ComboBox<String> comboResultFilter;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TableView<?> tableHistory;

    @FXML
    private Label lblTotalBets;

    @FXML
    private Label lblWonBets;

    @FXML
    private Label lblLostBets;

    @FXML
    private Label lblTotalBalance;

    @FXML
    private void initialize() {
        // Inicializar combo con valores si quieres, aunque ya está en FXML
        // comboResultFilter.getItems().addAll("Todas", "Ganadas", "Perdidas");

        // Inicializar estadísticas con valores por defecto
        lblTotalBets.setText("0");
        lblWonBets.setText("0");
        lblLostBets.setText("0");
        lblTotalBalance.setText("0 puntos");
    }

    @FXML
    private void handleBack() {
        // Lógica para volver a la pantalla anterior
        System.out.println("Volver a la pantalla anterior");
    }

    @FXML
    private void handleFilter() {
        // Lógica para filtrar la tabla según los criterios seleccionados
        System.out.println("Filtrando apuestas con resultado: " + comboResultFilter.getValue() +
            ", desde: " + dateFrom.getValue() + ", hasta: " + dateTo.getValue());
    }

    @FXML
    private void handleRefresh() {
        // Lógica para refrescar la tabla y estadísticas
        System.out.println("Actualizando datos de apuestas");
    }
}
