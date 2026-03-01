package controller.partidos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Partido;

import java.io.IOException;

public class MatchDetailController {

    @FXML
    private Label lblMatchDate;
    @FXML
    private Label lblMatchStatus;
    @FXML
    private Label lblHomeTeam;
    @FXML
    private Label lblAwayTeam;
    @FXML
    private Label lblHomeScore;
    @FXML
    private Label lblAwayScore;

    @FXML
    private TableView<String> tableStats;
    @FXML
    private TableColumn<String, String> colHomeStat;
    @FXML
    private TableColumn<String, String> colAwayStat;

    private Partido partido;

    public void setPartido(Partido partido) {
        this.partido = partido;
        cargarDatos();
    }

    private void cargarDatos() {

        lblMatchDate.setText("📅 " + partido.getFecha());
        lblMatchStatus.setText("Estado: " + partido.getEstado());

        lblHomeTeam.setText(partido.getEquipoLocal().getNombre());
        lblAwayTeam.setText(partido.getEquipoVisitante().getNombre());

        lblHomeScore.setText(String.valueOf(partido.getPuntosLocal()));
        lblAwayScore.setText(String.valueOf(partido.getPuntosVisitante()));

        cargarEstadisticas();
    }

    private void cargarEstadisticas() {

        colHomeStat.setCellValueFactory(data -> new SimpleStringProperty("100"));

        colAwayStat.setCellValueFactory(data -> new SimpleStringProperty("95"));

        tableStats.setItems(FXCollections.observableArrayList(
                "Tiros de Campo",
                "Rebotes",
                "Asistencias"));
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/layout/MainLayout.fxml"));

            Parent root = loader.load();

            // Obtener el controlador del layout
            controller.layout.MainLayoutController mainController = loader.getController();

            // Cargar la vista de partidos dentro del layout
            mainController.loadMatches();

            // Cambiar la escena
            Stage stage = (Stage) lblMatchDate.getScene().getWindow();
            Scene scene = new Scene(root);

            // Aplicar CSS
            scene.getStylesheets().add(
                    getClass().getResource("/styles/main.css").toExternalForm());

            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateBet() {
        System.out.println("Abrir ventana crear apuesta con partido preseleccionado");
    }

    @FXML
    private void handleViewStats() {
        System.out.println("Ver estadísticas detalladas");
    }
}