package controller.apuestas;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Apuesta;
import service.ApuestaService;
import session.Session;

public class BetsController {

    @FXML
    private TabPane tabPane;

    @FXML
    private ComboBox<String> comboFilterActive;
    @FXML
    private ComboBox<String> comboFilterHistory;

    @FXML
    private TableView<Apuesta> tableActiveBets;
    @FXML
    private TableView<Apuesta> tableBetHistory;

    @FXML
    private Label lblTotalBet;
    @FXML
    private Label lblTotalWins;
    @FXML
    private Label lblWinRate;

    private final ApuestaService apuestaService = new ApuestaService();

    private ObservableList<Apuesta> todasLasApuestas = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        configurarTablas();
        configurarFiltros();

        cargarApuestas();
    }

    // ===============================
    // CONFIGURACIÓN DE TABLAS
    // ===============================
    private void configurarTablas() {

        // ===== TABLA ACTIVAS =====
        TableColumn<Apuesta, String> colMatchActive = new TableColumn<>("Partido");
        colMatchActive.setCellValueFactory(data
                -> new SimpleStringProperty(
                        data.getValue().getPartido().getEquipoLocal().getNombre()
                        + " vs "
                        + data.getValue().getPartido().getEquipoVisitante().getNombre()
                )
        );

        TableColumn<Apuesta, String> colPredictionActive = new TableColumn<>("Predicción");
        colPredictionActive.setCellValueFactory(data
                -> new SimpleStringProperty(data.getValue().getPrediccion())
        );

        TableColumn<Apuesta, String> colPointsActive = new TableColumn<>("Puntos");
        colPointsActive.setCellValueFactory(data
                -> new SimpleStringProperty(String.valueOf(data.getValue().getPuntosApostados()))
        );

        tableActiveBets.getColumns().setAll(
                colMatchActive,
                colPredictionActive,
                colPointsActive
        );

        // ===== TABLA HISTORIAL =====
        TableColumn<Apuesta, String> colMatchHistory = new TableColumn<>("Partido");
        colMatchHistory.setCellValueFactory(data
                -> new SimpleStringProperty(
                        data.getValue().getPartido().getEquipoLocal().getNombre()
                        + " vs "
                        + data.getValue().getPartido().getEquipoVisitante().getNombre()
                )
        );

        TableColumn<Apuesta, String> colPredictionHistory = new TableColumn<>("Predicción");
        colPredictionHistory.setCellValueFactory(data
                -> new SimpleStringProperty(data.getValue().getPrediccion())
        );

        TableColumn<Apuesta, String> colPointsHistory = new TableColumn<>("Puntos");
        colPointsHistory.setCellValueFactory(data
                -> new SimpleStringProperty(String.valueOf(data.getValue().getPuntosApostados()))
        );

        TableColumn<Apuesta, String> colResultHistory = new TableColumn<>("Resultado");
        colResultHistory.setCellValueFactory(data
                -> new SimpleStringProperty(data.getValue().getResultado())
        );

        tableBetHistory.getColumns().setAll(
                colMatchHistory,
                colPredictionHistory,
                colPointsHistory,
                colResultHistory
        );
    }

    // ===============================
    // CONFIGURACIÓN DE FILTROS
    // ===============================
    private void configurarFiltros() {

        comboFilterActive.getItems().addAll("Todas", "Pendientes");
        comboFilterActive.setValue("Todas");

        comboFilterHistory.getItems().addAll("Todas", "Ganadas", "Perdidas");
        comboFilterHistory.setValue("Todas");

        comboFilterActive.setOnAction(e -> aplicarFiltros());
        comboFilterHistory.setOnAction(e -> aplicarFiltros());
    }

    // ===============================
    // CARGAR DATOS DESDE BACKEND
    // ===============================
    private void cargarApuestas() {

        Long userId = Session.getCurrentUser().getId();

        List<Apuesta> apuestas = apuestaService.obtenerApuestasUsuario(userId);

        todasLasApuestas.setAll(apuestas);

        aplicarFiltros();
        actualizarEstadisticas();
    }

    // ===============================
    // FILTRADO
    // ===============================
    private void aplicarFiltros() {

        // ACTIVAS
        List<Apuesta> activas = todasLasApuestas.stream()
                .filter(Apuesta::isActiva)
                .collect(Collectors.toList());

        if ("Todas".equals(comboFilterActive.getValue())) {
            tableActiveBets.setItems(FXCollections.observableArrayList(activas));
        } else {
            tableActiveBets.setItems(FXCollections.observableArrayList(activas));
        }

        // HISTORIAL
        List<Apuesta> historial = todasLasApuestas.stream()
                .filter(b -> b.isGanada() || b.isPerdida())
                .collect(Collectors.toList());

        String filtro = comboFilterHistory.getValue();

        if ("Ganadas".equals(filtro)) {
            historial = historial.stream().filter(Apuesta::isGanada).toList();
        }

        if ("Perdidas".equals(filtro)) {
            historial = historial.stream().filter(Apuesta::isPerdida).toList();
        }

        tableBetHistory.setItems(FXCollections.observableArrayList(historial));
    }

    // ===============================
    // ESTADÍSTICAS
    // ===============================
    private void actualizarEstadisticas() {

        int total = todasLasApuestas.size();
        int ganadas = (int) todasLasApuestas.stream().filter(Apuesta::isGanada).count();

        double porcentaje = total > 0
                ? (ganadas * 100.0) / total
                : 0;

        lblTotalBet.setText(String.valueOf(total));
        lblTotalWins.setText(String.valueOf(ganadas));
        lblWinRate.setText(String.format("%.1f %%", porcentaje));
    }

    // ===============================
    // BOTONES REFRESH
    // ===============================
    @FXML
    private void handleRefreshActive() {
        cargarApuestas();
    }

    @FXML
    private void handleRefreshHistory() {
        cargarApuestas();
    }
}
