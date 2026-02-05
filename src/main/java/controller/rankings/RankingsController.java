package controller.rankings;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class RankingsController {

    @FXML
    private ComboBox<String> comboRankingType;

    @FXML
    private Label lblFirstPlace;

    @FXML
    private Label lblFirstPoints;

    @FXML
    private Label lblSecondPlace;

    @FXML
    private Label lblSecondPoints;

    @FXML
    private Label lblThirdPlace;

    @FXML
    private Label lblThirdPoints;

    @FXML
    private TableView<?> tableRankings;

    @FXML
    private Label lblUserPosition;

    @FXML
    private Label lblUserPoints;

    @FXML
    private void initialize() {
        // Inicialización del controlador
        if (comboRankingType != null) {
            comboRankingType.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void handleRefresh() {
        // Lógica para actualizar el ranking
        System.out.println("Actualizando rankings...");
        // TODO: Implementar lógica de actualización
    }
}
