package controller.estadisticas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import model.Jugador;
import service.EquipoApiService;

import java.util.List;

public class PlayersStatsController {

    @FXML
    private TableView<Jugador> tablePlayers;

    @FXML
    private TableColumn<Jugador, String> colName;

    private ObservableList<Jugador> masterList = FXCollections.observableArrayList();
    private final EquipoApiService apiService = new EquipoApiService();

    @FXML
    public void initialize() {

        // IMPORTANTE → usar "nombre" porque viene del backend
        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        cargarJugadores();
    }

    private void cargarJugadores() {
        try {
            List<Jugador> jugadores = apiService.obtenerTodosJugadores();
            masterList.setAll(jugadores);
            tablePlayers.setItems(masterList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchPlayer(String name) {

        if (name == null || name.isEmpty()) {
            tablePlayers.setItems(masterList);
            return;
        }

        tablePlayers.setItems(
                masterList.filtered(player ->
                        player.getNombre().toLowerCase().contains(name.toLowerCase())
                )
        );
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/estadisticas/StatisticsView.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) tablePlayers.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}