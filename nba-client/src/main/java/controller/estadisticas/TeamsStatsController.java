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

    @FXML private TableView<Equipo> tableTeams;
    @FXML private TableColumn<Equipo, String> colEquipo;
    @FXML private TableColumn<Equipo, String> colConferencia;
    @FXML private TableColumn<Equipo, String> colDivision;
    @FXML private TableColumn<Equipo, Integer> colVictorias;
    @FXML private TableColumn<Equipo, Integer> colDerrotas;
    @FXML private TableColumn<Equipo, Double> colPPG;
    @FXML private TableColumn<Equipo, Double> colRPG;
    @FXML private TableColumn<Equipo, Double> colAPG;

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
                    getClass().getResource("/ui/estadisticas/TeamDetailView.fxml")
            );
            javafx.scene.Parent root = loader.load();

            controller.estadisticas.TeamDetailController controller = loader.getController();
            controller.setEquipo(equipo);

            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Detalle del Equipo");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Este es el método que faltaba
    @FXML
    private void handleBack() {
        // Cierra la ventana actual
        Stage stage = (Stage) tableTeams.getScene().getWindow();
        stage.close();
    }
}