package controller.estadisticas;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Equipo;
import model.Jugador;
import model.Partido;
import service.EquipoApiService;

public class TeamDetailController {

    private static final String CSS_PATH = "/styles/main.css";

    @FXML
    private ImageView imgTeamLogo;
    @FXML
    private Label lblTeamName, lblConference, lblDivision, lblRecord;
    @FXML
    private Label lblPPG, lblRPG, lblAPG;

    @FXML
    private TableView<Jugador> tablePlayers;
    @FXML
    private TableColumn<Jugador, String> colPlayerName, colPlayerPosition;
    @FXML
    private TableColumn<Jugador, Integer> colPlayerNumber;
    @FXML
    private TableColumn<Jugador, Double> colPlayerPPG, colPlayerRPG, colPlayerAPG;

    @FXML
    private TableView<Partido> tableRecentMatches;
    @FXML
    private TableColumn<Partido, String> colMatchDate, colMatchRival, colMatchResult;

    private final EquipoApiService apiService = new EquipoApiService();

    private Equipo equipoActual;

    public void setEquipo(Equipo equipo) {
        this.equipoActual = equipo;

        lblTeamName.setText(equipo.getNombre());
        lblConference.setText(equipo.getConferencia());
        lblDivision.setText(equipo.getDivision());
        lblRecord.setText("Record: " + equipo.getVictorias() + "-" + equipo.getDerrotas());
        lblPPG.setText(String.valueOf(equipo.getPpg()));
        lblRPG.setText(String.valueOf(equipo.getRpg()));
        lblAPG.setText(String.valueOf(equipo.getApg()));

        try {
            List<Jugador> jugadores = apiService.obtenerJugadoresEquipo(equipo.getId());
            ObservableList<Jugador> obsJugadores = FXCollections.observableArrayList(jugadores);
            tablePlayers.setItems(obsJugadores);

            colPlayerName.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
            colPlayerPosition.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPosicion()));
            colPlayerNumber.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumero()).asObject());
            colPlayerPPG.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPpg()).asObject());
            colPlayerRPG.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getRpg()).asObject());
            colPlayerAPG.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getApg()).asObject());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Partido> partidos = apiService.obtenerPartidosEquipo(equipo.getId());

            List<Partido> partidosJugados = partidos.stream()
                    .filter(p -> p.getEstado() != null && !"PROGRAMADO".equalsIgnoreCase(p.getEstado()))
                    .toList();

            ObservableList<Partido> obsPartidos = FXCollections.observableArrayList(partidosJugados);
            tableRecentMatches.setItems(obsPartidos);

            colMatchDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getFecha() != null ? data.getValue().getFecha().toString() : "N/A"));
            colMatchRival.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getRival(equipo.getId())));
            colMatchResult.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getResultado(equipo.getId())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/ui/estadisticas/TeamsStatsView.fxml"));
            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) lblTeamName.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
