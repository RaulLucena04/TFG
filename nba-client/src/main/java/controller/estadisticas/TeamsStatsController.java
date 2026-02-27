package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Equipo;
import service.EquipoApiService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TeamsStatsController implements Initializable {

    @FXML
    private TableView<Equipo> tableTeams;
    @FXML
    private TableColumn<Equipo, String> colEquipo;
    @FXML
    private TableColumn<Equipo, String> colConferencia;
    @FXML
    private TableColumn<Equipo, String> colDivision;
    @FXML
    private TableColumn<Equipo, Integer> colVictorias;
    @FXML
    private TableColumn<Equipo, Integer> colDerrotas;
    @FXML
    private TableColumn<Equipo, Double> colPPG;
    @FXML
    private TableColumn<Equipo, Double> colRPG;
    @FXML
    private TableColumn<Equipo, Double> colAPG;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configura las columnas
        colEquipo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colConferencia.setCellValueFactory(new PropertyValueFactory<>("conferencia"));
        colDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        colVictorias.setCellValueFactory(new PropertyValueFactory<>("victorias"));
        colDerrotas.setCellValueFactory(new PropertyValueFactory<>("derrotas"));
        colPPG.setCellValueFactory(new PropertyValueFactory<>("ppg"));
        colRPG.setCellValueFactory(new PropertyValueFactory<>("rpg"));
        colAPG.setCellValueFactory(new PropertyValueFactory<>("apg"));

        cargarEquipos();

        // Doble clic abre detalle
        tableTeams.setRowFactory(tv -> {
            TableRow<Equipo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    abrirDetalle(row.getItem());
                }
            });
            return row;
        });
    }

    private void cargarEquipos() {
        try {
            EquipoApiService api = new EquipoApiService();
            List<Equipo> lista = api.obtenerEquipos();
            tableTeams.getItems().setAll(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirDetalle(Equipo equipo) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/ui/estadisticas/TeamDetailView.fxml"));
            javafx.scene.Parent root = loader.load();

            TeamDetailController controller = loader.getController();
            controller.setEquipo(equipo);

            Stage stage = (Stage) tableTeams.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/ui/estadisticas/StatisticsView.fxml"));
            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) tableTeams.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTeam(String name) {

    if (name == null || name.isEmpty()) {
        cargarEquipos(); // recarga desde API
        return;
    }

    tableTeams.setItems(
            tableTeams.getItems().filtered(team ->
                    team.getNombre().toLowerCase().contains(name.toLowerCase())
            )
    );
}
}