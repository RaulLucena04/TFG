package controller.estadisticas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Equipo;
import service.EquipoApiService;

public class TeamsStatsController implements Initializable {

    private static final String CSS_PATH = "/styles/main.css";

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
        colEquipo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colConferencia.setCellValueFactory(new PropertyValueFactory<>("conferencia"));
        colDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        colVictorias.setCellValueFactory(new PropertyValueFactory<>("victorias"));
        colDerrotas.setCellValueFactory(new PropertyValueFactory<>("derrotas"));
        colPPG.setCellValueFactory(new PropertyValueFactory<>("ppg"));
        colRPG.setCellValueFactory(new PropertyValueFactory<>("rpg"));
        colAPG.setCellValueFactory(new PropertyValueFactory<>("apg"));

        cargarEquipos();

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
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/layout/MainLayout.fxml"));

            Parent root = loader.load();

            // Obtener el controlador del layout
            controller.layout.MainLayoutController mainController = loader.getController();

            // Opcional: cargar automáticamente la vista de estadísticas
            mainController.loadStatistics();

            Stage stage = (Stage) tableTeams.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());

            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTeam(String name) {
        if (name == null || name.isEmpty()) {
            cargarEquipos();
            return;
        }

        tableTeams.setItems(
                tableTeams.getItems().filtered(team
                        -> team.getNombre().toLowerCase().contains(name.toLowerCase())
                )
        );
    }
}
