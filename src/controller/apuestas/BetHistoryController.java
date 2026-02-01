package controller.apuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * Controlador de la vista de historial de apuestas.
 * Gestiona la inicialización de la interfaz y las acciones del usuario.
 */
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

    /**
     * Inicializa los valores por defecto mostrados en la interfaz.
     */
    @FXML
    private void initialize() {
        lblTotalBets.setText("0");
        lblWonBets.setText("0");
        lblLostBets.setText("0");
        lblTotalBalance.setText("0 puntos");
    }

    /**
     * Permite volver a la pantalla anterior.
     */
    @FXML
    private void handleBack() {
        System.out.println("Volver a la pantalla anterior");
    }

    /**
     * Aplica los filtros seleccionados al historial de apuestas.
     */
    @FXML
    private void handleFilter() {
        System.out.println(
                "Filtrando apuestas con resultado: " + comboResultFilter.getValue());
    }

    /**
     * Actualiza la tabla y las estadísticas mostradas.
     */
    @FXML
    private void handleRefresh() {
        System.out.println("Actualizando datos de apuestas");
    }
}
