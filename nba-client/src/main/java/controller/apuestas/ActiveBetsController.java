package controller.apuestas;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class ActiveBetsController {

    @FXML private ComboBox<String> comboFilter;
    @FXML private VBox betsContainer;

    @FXML
    private void initialize() {
        comboFilter.getItems().addAll(
            "Todas",
            "Pendientes",
            "En curso"
        );
        comboFilter.setValue("Todas");
    }

    @FXML
    private void handleBack() {
        // navegación atrás
    }

    @FXML
    private void handleRefresh() {
        // recargar apuestas
    }
}
