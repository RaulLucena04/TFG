package controller.partidos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class MatchesController {

    @FXML
    private ComboBox<String> comboFilter;

    @FXML
    private VBox matchesContainer;

    @FXML
    private void initialize() {
        // Inicialización del controlador
        if (comboFilter != null) {
            comboFilter.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void handleRefresh() {
        // Lógica para actualizar la lista de partidos
        System.out.println("Actualizando partidos...");
        // TODO: Implementar lógica de actualización
    }
}
