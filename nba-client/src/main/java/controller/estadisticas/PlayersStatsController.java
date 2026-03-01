package controller.estadisticas;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Jugador;
import service.EquipoApiService;

public class PlayersStatsController {

    private static final String CSS_PATH = "/styles/main.css";

    @FXML
    private TableView<Jugador> tablePlayers;

    @FXML
    private TableColumn<Jugador, String> colName;

    private ObservableList<Jugador> masterList = FXCollections.observableArrayList();
    private final EquipoApiService apiService = new EquipoApiService();

    @FXML
    public void initialize() {

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
            Scene scene = new Scene(root);

            // AÑADIR CSS
            scene.getStylesheets().add(
                    getClass().getResource(CSS_PATH).toExternalForm()
            );

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
