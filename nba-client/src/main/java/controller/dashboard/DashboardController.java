package controller.dashboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import model.Apuesta;
import model.Partido;
import model.User;
import service.ApuestaService;
import service.PartidoService;
import service.UsuarioService;
import session.Session;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML
    private Label lblTotalPoints;
    @FXML
    private Label lblActiveBets;
    @FXML
    private Label lblWinRate;
    @FXML
    private Label lblRanking;

    @FXML
    private TableView<Partido> tableUpcomingMatches;
    @FXML
    private Pane chartContainer;

    private final ApuestaService apuestaService = new ApuestaService();
    private final PartidoService partidoService = new PartidoService();
    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void initialize() {
        cargarResumen();
        cargarPartidos();
        cargarGrafica();
        cargarRanking();
    }

    // ==========================================
    // RESUMEN SUPERIOR
    // ==========================================
    private void cargarResumen() {

        Long userId = Session.getCurrentUser().getId();
        List<Apuesta> apuestas = apuestaService.obtenerApuestasUsuario(userId);

        int activas = (int) apuestas.stream().filter(Apuesta::isActiva).count();
        int ganadas = (int) apuestas.stream().filter(Apuesta::isGanada).count();
        int finalizadas = (int) apuestas.stream()
                .filter(a -> a.isGanada() || a.isPerdida())
                .count();

        int puntosGanados = apuestas.stream()
                .filter(Apuesta::isGanada)
                .mapToInt(Apuesta::getPuntosApostados)
                .sum();

        double winRate = finalizadas > 0
                ? (ganadas * 100.0) / finalizadas
                : 0;

        lblTotalPoints.setText(String.valueOf(puntosGanados));
        lblActiveBets.setText(String.valueOf(activas));
        lblWinRate.setText(String.format("%.1f %%", winRate));

        // Simulado por ahora
        lblRanking.setText("#" + (int) (Math.random() * 50 + 1));
    }

    // ==========================================
    // TABLA PARTIDOS PRÓXIMOS
    // ==========================================
    private void cargarPartidos() {

        List<Partido> partidos = partidoService.listarPartidos();

        TableColumn<Partido, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha().toString()));

        TableColumn<Partido, String> colLocal = new TableColumn<>("Equipo Local");
        colLocal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEquipoLocal().getNombre()));

        TableColumn<Partido, String> colVisitante = new TableColumn<>("Equipo Visitante");
        colVisitante.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getEquipoVisitante().getNombre()));

        tableUpcomingMatches.getColumns().setAll(colFecha, colLocal, colVisitante);
        tableUpcomingMatches.setItems(FXCollections.observableArrayList(partidos));
    }

    // ==========================================
    // GRÁFICA RENDIMIENTO
    // ==========================================
    private void cargarGrafica() {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        xAxis.setLabel("Últimas Apuestas");
        yAxis.setLabel("Puntos");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        Long userId = Session.getCurrentUser().getId();
        List<Apuesta> apuestas = apuestaService.obtenerApuestasUsuario(userId);

        List<Apuesta> ultimas = apuestas.stream()
                .limit(5)
                .collect(Collectors.toList());

        int index = 1;
        for (Apuesta a : ultimas) {
            int valor = a.isGanada()
                    ? a.getPuntosApostados()
                    : -a.getPuntosApostados();

            series.getData().add(
                    new XYChart.Data<>("Apuesta " + index, valor));
            index++;
        }

        lineChart.getData().add(series);
        lineChart.setPrefHeight(280);

        chartContainer.getChildren().clear();
        chartContainer.getChildren().add(lineChart);
    }

    private void cargarRanking() {

        java.util.List<User> usuariosOrdenados = usuarioService.listarUsuarios();
        Long currentId = Session.getCurrentUser().getId();

        int posicion = 1;

        for (User u : usuariosOrdenados) {
            if (u.getId().equals(currentId)) {
                break;
            }
            posicion++;
        }

        lblRanking.setText("#" + posicion);
    }
}