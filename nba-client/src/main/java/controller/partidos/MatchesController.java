package controller.partidos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Partido;
import service.PartidoService;

import java.io.IOException;
import java.util.List;

public class MatchesController {

    @FXML private VBox matchesContainer;
    @FXML private ComboBox<String> comboFilter;

    private final PartidoService partidoService = new PartidoService();
    private List<Partido> todosLosPartidos;

    @FXML
    private void initialize() {

        comboFilter.setValue("Todos");
        comboFilter.setOnAction(e -> aplicarFiltro());

        cargarPartidos();
    }

    private void cargarPartidos() {
        todosLosPartidos = partidoService.listarPartidos();
        aplicarFiltro();
    }

    private void aplicarFiltro() {

        matchesContainer.getChildren().clear();

        String filtro = comboFilter.getValue();

        List<Partido> filtrados = todosLosPartidos;

        if (filtro != null) {

            switch (filtro) {

                case "Próximos":
                    filtrados = todosLosPartidos.stream()
                            .filter(p -> "PROGRAMADO".equalsIgnoreCase(p.getEstado()))
                            .toList();
                    break;

                case "En curso":
                    filtrados = todosLosPartidos.stream()
                            .filter(p -> "EN_CURSO".equalsIgnoreCase(p.getEstado()))
                            .toList();
                    break;

                case "Finalizados":
                    filtrados = todosLosPartidos.stream()
                            .filter(p -> "FINALIZADO".equalsIgnoreCase(p.getEstado()))
                            .toList();
                    break;
            }
        }

        for (Partido partido : filtrados) {
            matchesContainer.getChildren().add(crearCard(partido));
        }
    }

    private Node crearCard(Partido partido) {

        VBox card = new VBox();
        card.setSpacing(8);
        card.getStyleClass().add("match-card");

        Label lblEquipos = new Label(
                partido.getEquipoLocal().getNombre()
                        + " vs "
                        + partido.getEquipoVisitante().getNombre()
        );
        lblEquipos.getStyleClass().add("match-title");

        Label lblFecha = new Label("📅 " + partido.getFecha());

        Label lblEstado = new Label("Estado: " + partido.getEstado());

        // 🎨 Colores por estado
        switch (partido.getEstado()) {
            case "PROGRAMADO":
                lblEstado.setStyle("-fx-text-fill: #f39c12;"); // naranja
                break;
            case "EN_CURSO":
                lblEstado.setStyle("-fx-text-fill: #3498db;"); // azul
                break;
            case "FINALIZADO":
                lblEstado.setStyle("-fx-text-fill: #2ecc71;"); // verde
                break;
        }

        // Mostrar marcador solo si finalizado
        Label lblMarcador = new Label();
        if ("FINALIZADO".equalsIgnoreCase(partido.getEstado())) {
            lblMarcador.setText(
                    partido.getPuntosLocal()
                            + " - "
                            + partido.getPuntosVisitante()
            );
        }

        Button btnDetalle = new Button("Ver Detalle");
        btnDetalle.setOnAction(e -> abrirDetalle(partido));

        card.getChildren().addAll(lblEquipos, lblFecha, lblEstado, lblMarcador, btnDetalle);

        return card;
    }

    private void abrirDetalle(Partido partido) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/partidos/MatchDetailView.fxml")
            );

            VBox root = loader.load();

           MatchDetailController controller =
                    loader.getController();

            controller.setPartido(partido);

            matchesContainer.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        cargarPartidos();
    }
}