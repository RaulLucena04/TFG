package controller.rankings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Apuesta;
import model.User;
import service.ApuestaService;
import service.UsuarioService;
import session.Session;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankingsController {

    @FXML
    private ComboBox<String> comboRankingType;

    @FXML
    private Label lblFirstPlace;

    @FXML
    private Label lblFirstPoints;

    @FXML
    private Label lblSecondPlace;

    @FXML
    private Label lblSecondPoints;

    @FXML
    private Label lblThirdPlace;

    @FXML
    private Label lblThirdPoints;

    @FXML
    private TableView<RankingRow> tableRankings;

    @FXML
    private Label lblUserPosition;

    @FXML
    private Label lblUserPoints;

    private UsuarioService usuarioService;
    private ApuestaService apuestaService;

    @FXML
    private void initialize() {
        usuarioService = new UsuarioService();
        apuestaService = new ApuestaService();

        if (comboRankingType != null) {
            comboRankingType.getSelectionModel().selectFirst();
            comboRankingType.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    cargarRankings();
                }
            });
        }

        configurarTabla();
        cargarRankings();
    }

    private void configurarTabla() {
        TableColumn<RankingRow, Integer> colPosicion = new TableColumn<>("Posición");
        colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        colPosicion.setPrefWidth(100);

        TableColumn<RankingRow, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colUsuario.setPrefWidth(200);

        TableColumn<RankingRow, Integer> colPuntos = new TableColumn<>("Puntos Totales");
        colPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));
        colPuntos.setPrefWidth(150);

        TableColumn<RankingRow, String> colTasaAciertos = new TableColumn<>("Tasa de Aciertos");
        colTasaAciertos.setCellValueFactory(new PropertyValueFactory<>("tasaAciertos"));
        colTasaAciertos.setPrefWidth(130);

        TableColumn<RankingRow, Integer> colTotalApuestas = new TableColumn<>("Apuestas Totales");
        colTotalApuestas.setCellValueFactory(new PropertyValueFactory<>("totalApuestas"));
        colTotalApuestas.setPrefWidth(130);

        TableColumn<RankingRow, Integer> colApuestasGanadas = new TableColumn<>("Apuestas Ganadas");
        colApuestasGanadas.setCellValueFactory(new PropertyValueFactory<>("apuestasGanadas"));
        colApuestasGanadas.setPrefWidth(130);

        TableColumn<RankingRow, Integer> colGanancias = new TableColumn<>("Ganancias Totales");
        colGanancias.setCellValueFactory(new PropertyValueFactory<>("ganancias"));
        colGanancias.setPrefWidth(150);

        tableRankings.getColumns().setAll(colPosicion, colUsuario, colPuntos, colTasaAciertos, 
                                          colTotalApuestas, colApuestasGanadas, colGanancias);
    }

    private void cargarRankings() {
        String tipoRanking = comboRankingType.getValue();
        if (tipoRanking == null) {
            tipoRanking = "Puntos Totales";
        }

        try {
            List<User> usuarios = usuarioService.listarUsuarios();
            
            // Calcular estadísticas para cada usuario
            List<RankingRow> rankingRows = usuarios.stream()
                    .map(this::calcularEstadisticasUsuario)
                    .collect(Collectors.toList());

            // Ordenar según el tipo de ranking seleccionado
            switch (tipoRanking) {
                case "Tasa de Aciertos":
                    rankingRows.sort(Comparator
                            .comparingDouble((RankingRow r) -> parseTasaAciertos(r.getTasaAciertos()))
                            .reversed()
                            .thenComparingInt(RankingRow::getPuntos).reversed());
                    break;
                case "Ganancias Totales":
                    rankingRows.sort(Comparator
                            .comparingInt(RankingRow::getGanancias)
                            .reversed()
                            .thenComparingInt(RankingRow::getPuntos).reversed());
                    break;
                case "Apuestas Ganadas":
                    rankingRows.sort(Comparator
                            .comparingInt(RankingRow::getApuestasGanadas)
                            .reversed()
                            .thenComparingInt(RankingRow::getPuntos).reversed());
                    break;
                default: // "Puntos Totales"
                    rankingRows.sort(Comparator
                            .comparingInt(RankingRow::getPuntos)
                            .reversed());
                    break;
            }

            // Asignar posiciones
            for (int i = 0; i < rankingRows.size(); i++) {
                rankingRows.get(i).setPosicion(i + 1);
            }

            // Mostrar top 3
            mostrarTop3(rankingRows);

            // Mostrar tabla completa
            ObservableList<RankingRow> rows = FXCollections.observableArrayList(rankingRows);
            tableRankings.setItems(rows);

            // Mostrar posición del usuario actual
            mostrarPosicionUsuario(rankingRows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RankingRow calcularEstadisticasUsuario(User usuario) {
        Long userId = usuario.getId();
        List<Apuesta> apuestas = apuestaService.obtenerApuestasUsuario(userId);

        int totalApuestas = apuestas.size();
        long apuestasGanadas = apuestas.stream()
                .filter(Apuesta::isGanada)
                .count();
        long apuestasFinalizadas = apuestas.stream()
                .filter(a -> a.isGanada() || a.isPerdida())
                .count();

        double tasaAciertos = apuestasFinalizadas > 0
                ? (apuestasGanadas * 100.0) / apuestasFinalizadas
                : 0.0;

        // Calcular ganancias totales (apuestas ganadas * cuota - apuestas perdidas)
        int ganancias = apuestas.stream()
                .mapToInt(a -> {
                    if (a.isGanada()) {
                        Double cuota = a.getCuota();
                        if (cuota != null && cuota > 0) {
                            return (int) Math.round(a.getPuntosApostados() * cuota);
                        }
                        return a.getPuntosApostados() * 2; // Fallback si no hay cuota
                    } else if (a.isPerdida()) {
                        return -a.getPuntosApostados(); // Pérdida
                    }
                    return 0; // Pendiente
                })
                .sum();

        RankingRow row = new RankingRow();
        row.setUsuario(usuario.getUsername());
        row.setPuntos(usuario.getPoints());
        row.setTasaAciertos(String.format("%.1f%%", tasaAciertos));
        row.setTotalApuestas(totalApuestas);
        row.setApuestasGanadas((int) apuestasGanadas);
        row.setGanancias(ganancias);
        row.setUserId(usuario.getId());

        return row;
    }

    private double parseTasaAciertos(String tasa) {
        try {
            return Double.parseDouble(tasa.replace("%", ""));
        } catch (Exception e) {
            return 0.0;
        }
    }

    private void mostrarTop3(List<RankingRow> rankingRows) {
        if (rankingRows.size() >= 3) {
            // Segundo lugar
            RankingRow segundo = rankingRows.get(1);
            lblSecondPlace.setText(segundo.getUsuario());
            lblSecondPoints.setText(segundo.getPuntos() + " puntos");

            // Primer lugar
            RankingRow primero = rankingRows.get(0);
            lblFirstPlace.setText(primero.getUsuario());
            lblFirstPoints.setText(primero.getPuntos() + " puntos");

            // Tercer lugar
            RankingRow tercero = rankingRows.get(2);
            lblThirdPlace.setText(tercero.getUsuario());
            lblThirdPoints.setText(tercero.getPuntos() + " puntos");
        } else if (rankingRows.size() == 2) {
            RankingRow primero = rankingRows.get(0);
            RankingRow segundo = rankingRows.get(1);

            lblFirstPlace.setText(primero.getUsuario());
            lblFirstPoints.setText(primero.getPuntos() + " puntos");
            lblSecondPlace.setText(segundo.getUsuario());
            lblSecondPoints.setText(segundo.getPuntos() + " puntos");
            lblThirdPlace.setText("-");
            lblThirdPoints.setText("-");
        } else if (rankingRows.size() == 1) {
            RankingRow primero = rankingRows.get(0);
            lblFirstPlace.setText(primero.getUsuario());
            lblFirstPoints.setText(primero.getPuntos() + " puntos");
            lblSecondPlace.setText("-");
            lblSecondPoints.setText("-");
            lblThirdPlace.setText("-");
            lblThirdPoints.setText("-");
        } else {
            lblFirstPlace.setText("-");
            lblFirstPoints.setText("-");
            lblSecondPlace.setText("-");
            lblSecondPoints.setText("-");
            lblThirdPlace.setText("-");
            lblThirdPoints.setText("-");
        }
    }

    private void mostrarPosicionUsuario(List<RankingRow> rankingRows) {
        User usuarioActual = Session.getCurrentUser();
        if (usuarioActual == null) {
            lblUserPosition.setText("#?");
            lblUserPoints.setText("0");
            return;
        }

        for (RankingRow row : rankingRows) {
            if (row.getUserId().equals(usuarioActual.getId())) {
                lblUserPosition.setText("#" + row.getPosicion());
                lblUserPoints.setText(String.valueOf(row.getPuntos()));
                return;
            }
        }

        lblUserPosition.setText("#?");
        lblUserPoints.setText(String.valueOf(usuarioActual.getPoints()));
    }

    @FXML
    private void handleRefresh() {
        cargarRankings();
    }

    // Clase auxiliar para la tabla de rankings
    public static class RankingRow {
        private int posicion;
        private String usuario;
        private int puntos;
        private String tasaAciertos;
        private int totalApuestas;
        private int apuestasGanadas;
        private int ganancias;
        private Long userId;

        public int getPosicion() { return posicion; }
        public void setPosicion(int posicion) { this.posicion = posicion; }

        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }

        public int getPuntos() { return puntos; }
        public void setPuntos(int puntos) { this.puntos = puntos; }

        public String getTasaAciertos() { return tasaAciertos; }
        public void setTasaAciertos(String tasaAciertos) { this.tasaAciertos = tasaAciertos; }

        public int getTotalApuestas() { return totalApuestas; }
        public void setTotalApuestas(int totalApuestas) { this.totalApuestas = totalApuestas; }

        public int getApuestasGanadas() { return apuestasGanadas; }
        public void setApuestasGanadas(int apuestasGanadas) { this.apuestasGanadas = apuestasGanadas; }

        public int getGanancias() { return ganancias; }
        public void setGanancias(int ganancias) { this.ganancias = ganancias; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }
}
