package controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MatchFormController {

    @FXML
    private Label lblFormTitle;

    @FXML
    private ComboBox<String> comboHomeTeam;

    @FXML
    private ComboBox<String> comboAwayTeam;

    @FXML
    private ComboBox<String> comboStatus;

    @FXML
    private DatePicker dateMatch;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtHomeScore;

    @FXML
    private TextField txtAwayScore;

    @FXML
    private TextArea txtDescription;

    @FXML
    private Label lblError;

    @FXML
    private void initialize() {
        // Estados del partido
        comboStatus.getItems().addAll(
            "Programado",
            "En curso",
            "Finalizado"
        );

        // Datos de ejemplo (puedes quitarlos)
        comboHomeTeam.getItems().addAll("Lakers", "Celtics", "Bulls");
        comboAwayTeam.getItems().addAll("Warriors", "Heat", "Nets");
    }

    @FXML
    private void handleBack() {
        // navegación futura
    }

    @FXML
    private void handleCancel() {
        // limpiar o volver
    }

    @FXML
    private void handleSave() {
        // guardar partido (más adelante)
    }
}
