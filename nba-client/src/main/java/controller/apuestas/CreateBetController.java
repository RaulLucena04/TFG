package controller.apuestas;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Apuesta;
import model.Partido;
import service.ApuestaService;
import service.PartidoService;
import session.Session;

import java.util.List;

public class CreateBetController {

    @FXML
    private ComboBox<Partido> comboPartido;
    @FXML
    private ComboBox<String> comboPrediccion;
    @FXML
    private TextField txtPuntos;

    @FXML
    private void initialize() {
        comboPrediccion.getItems().addAll("LOCAL", "VISITANTE");
    }

    private final PartidoService partidoService = new PartidoService();
    private final ApuestaService apuestaService = new ApuestaService();

    private void cargarPartidos() {
        List<Partido> partidos = partidoService.listarPartidos();
        comboPartido.setItems(FXCollections.observableArrayList(partidos));

        // Mostrar bonito en combo
        comboPartido.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Partido item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getEquipoLocal().getNombre()
                            + " vs "
                            + item.getEquipoVisitante().getNombre());
                }
            }
        });

        comboPartido.setButtonCell(comboPartido.getCellFactory().call(null));
    }

    @FXML
    private void handleSave() {
        try {

            Partido partido = comboPartido.getValue();
            String prediccion = comboPrediccion.getValue();
            int puntos = Integer.parseInt(txtPuntos.getText());

            Apuesta apuesta = new Apuesta();
            apuesta.setPartido(partido);
            apuesta.setPrediccion(prediccion);
            apuesta.setPuntosApostados(puntos);

            apuestaService.crearApuesta(apuesta);

            cerrarVentana();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) comboPartido.getScene().getWindow();
        stage.close();
    }
}