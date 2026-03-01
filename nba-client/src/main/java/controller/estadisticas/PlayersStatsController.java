package controller.estadisticas;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML
    private TableColumn<Jugador, String> colTeam;
    @FXML
    private TableColumn<Jugador, String> colPosition;
    @FXML
    private TableColumn<Jugador, Double> colPPG;
    @FXML
    private TableColumn<Jugador, Double> colRPG;
    @FXML
    private TableColumn<Jugador, Double> colAPG;
    @FXML
    private TableColumn<Jugador, Void> colAction;

    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> comboTeam;
    @FXML
    private ComboBox<String> comboPosition;

    private ObservableList<Jugador> masterList = FXCollections.observableArrayList();
    private final EquipoApiService apiService = new EquipoApiService();

    @FXML
    public void initialize() {

        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colTeam.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEquipoNombre()));

        colPosition.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        colPPG.setCellValueFactory(new PropertyValueFactory<>("ppg"));
        colRPG.setCellValueFactory(new PropertyValueFactory<>("rpg"));
        colAPG.setCellValueFactory(new PropertyValueFactory<>("apg"));

        cargarJugadores();
        cargarFiltros();
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

    private void cargarFiltros() {
        List<String> equipos = masterList.stream()
                .map(j -> j.getEquipo().getNombre())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        comboTeam.getItems().setAll("Todos");
        comboTeam.getItems().addAll(equipos);
        comboTeam.getSelectionModel().selectFirst();

        comboPosition.getSelectionModel().selectFirst();
    }

    private void aplicarFiltros() {
        String search = txtSearch.getText().toLowerCase();
        String team = comboTeam.getValue();
        String position = comboPosition.getValue();

        tablePlayers.setItems(masterList.filtered(player -> {

            boolean matchName = player.getNombre().toLowerCase().contains(search);
            boolean matchTeam = team.equals("Todos") || player.getEquipo().getNombre().equals(team);
            boolean matchPosition = position.equals("Todas") || player.getPosicion().equals(position);

            return matchName && matchTeam && matchPosition;
        }));
    }

    @FXML
    public void handleSearch() {
        aplicarFiltros();
    }

    @FXML
    public void handleRefresh() {
        cargarJugadores();
        cargarFiltros();
        aplicarFiltros();
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/layout/MainLayout.fxml"));

            Parent root = loader.load();

            // Obtener el controlador del layout principal
            controller.layout.MainLayoutController mainController = loader.getController();

            // Cargar la vista de estadísticas dentro del layout
            mainController.loadStatistics();

            // Cambiar la escena
            Stage stage = (Stage) tablePlayers.getScene().getWindow();
            Scene scene = new Scene(root);

            // Aplicar CSS global
            scene.getStylesheets().add(
                    getClass().getResource(CSS_PATH).toExternalForm());

            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
