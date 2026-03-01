package controller.apuestas;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Apuesta;
import service.ApuestaService;
import session.Session;

public class ActiveBetsController {

    @FXML private ComboBox<String> comboFilter;
    @FXML private VBox betsContainer;

    private final ApuestaService apuestaService = new ApuestaService();

    @FXML
    private void initialize() {

        comboFilter.getItems().addAll(
                "Todas",
                "Pendientes",
                "Ganadas",
                "Perdidas"
        );

        comboFilter.setValue("Todas");

        comboFilter.setOnAction(e -> cargarApuestas());

        cargarApuestas();
    }

    private void cargarApuestas() {

        betsContainer.getChildren().clear();

        Long userId = Session.getCurrentUser().getId();

        List<Apuesta> apuestas = apuestaService.obtenerApuestasUsuario(userId);

        String filtro = comboFilter.getValue();

        for (Apuesta bet : apuestas) {

            boolean mostrar = switch (filtro) {
                case "Pendientes" -> bet.isActiva();
                case "Ganadas" -> bet.isGanada();
                case "Perdidas" -> bet.isPerdida();
                default -> true;
            };

            if (mostrar) {
                betsContainer.getChildren().add(crearTarjetaApuesta(bet));
            }
        }
    }

    private HBox crearTarjetaApuesta(Apuesta bet) {

        HBox card = new HBox();
        card.setSpacing(20);
        card.setStyle("""
            -fx-padding: 15;
            -fx-background-color: #1e1e1e;
            -fx-background-radius: 10;
        """);

        String partidoTexto = bet.getPartido().getEquipoLocal()
                + " vs "
                + bet.getPartido().getEquipoVisitante();

        Label lblPartido = new Label(partidoTexto);
        lblPartido.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        Label lblPrediccion = new Label("Predicción: " + bet.getPrediccion());
        lblPrediccion.setStyle("-fx-text-fill: #cccccc;");

        Label lblPuntos = new Label("Puntos: " + bet.getPuntosApostados());
        lblPuntos.setStyle("-fx-text-fill: #cccccc;");

        Label lblEstado = new Label("Estado: " + bet.getResultado());

        if (bet.isGanada()) {
            lblEstado.setStyle("-fx-text-fill: #4CAF50;");
        } else if (bet.isPerdida()) {
            lblEstado.setStyle("-fx-text-fill: #F44336;");
        } else {
            lblEstado.setStyle("-fx-text-fill: #FFC107;");
        }

        VBox info = new VBox(lblPartido, lblPrediccion, lblPuntos, lblEstado);
        info.setSpacing(5);

        HBox.setHgrow(info, Priority.ALWAYS);

        card.getChildren().add(info);

        return card;
    }

    @FXML
    private void handleBack() {
        // navegación atrás
    }

    @FXML
    private void handleRefresh() {
        cargarApuestas();
    }
}