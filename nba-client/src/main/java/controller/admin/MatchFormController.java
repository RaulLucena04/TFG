package controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import model.Equipo;
import model.Partido;
import service.EquipoApiService;
import service.PartidoService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MatchFormController {

    @FXML
    private Label lblFormTitle;

    @FXML
    private ComboBox<Equipo> comboHomeTeam;

    @FXML
    private ComboBox<Equipo> comboAwayTeam;

    @FXML
    private ComboBox<String> comboStatus;

    @FXML
    private DatePicker dateMatch;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtHomeScore;

    @FXML
    private TextField txtAwayScore;

    @FXML
    private TextArea txtDescription;

    @FXML
    private Label lblError;

    private PartidoService partidoService;
    private EquipoApiService equipoService;
    private Partido partidoEditando;
    private boolean esEdicion = false;

    @FXML
    private void initialize() {
        partidoService = new PartidoService();
        equipoService = new EquipoApiService();

        // Estados del partido
        comboStatus.getItems().addAll(
            "Programado",
            "En curso",
            "Finalizado"
        );
        comboStatus.setValue("Programado");

        // Cargar equipos
        cargarEquipos();

        // Configurar DatePicker
        dateMatch.setValue(LocalDate.now());
    }

    private void cargarEquipos() {
        try {
            List<Equipo> equipos = equipoService.obtenerEquipos();
            ObservableList<Equipo> equiposList = FXCollections.observableArrayList(equipos);
            
            comboHomeTeam.setItems(equiposList);
            comboAwayTeam.setItems(equiposList);

            // Configurar CellFactory para mostrar el nombre del equipo
            comboHomeTeam.setCellFactory(param -> new ListCell<Equipo>() {
                @Override
                protected void updateItem(Equipo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre());
                    }
                }
            });

            comboHomeTeam.setButtonCell(new ListCell<Equipo>() {
                @Override
                protected void updateItem(Equipo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre());
                    }
                }
            });

            comboAwayTeam.setCellFactory(param -> new ListCell<Equipo>() {
                @Override
                protected void updateItem(Equipo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre());
                    }
                }
            });

            comboAwayTeam.setButtonCell(new ListCell<Equipo>() {
                @Override
                protected void updateItem(Equipo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar los equipos: " + e.getMessage());
        }
    }

    public void cargarPartido(Partido partido) {
        this.partidoEditando = partido;
        this.esEdicion = true;
        lblFormTitle.setText("Editar Partido");

        // Cargar datos del partido
        if (partido.getEquipoLocal() != null) {
            comboHomeTeam.setValue(partido.getEquipoLocal());
        }
        if (partido.getEquipoVisitante() != null) {
            comboAwayTeam.setValue(partido.getEquipoVisitante());
        }
        if (partido.getFecha() != null) {
            dateMatch.setValue(partido.getFecha());
        }
        if (partido.getEstado() != null) {
            comboStatus.setValue(partido.getEstado());
        }
        txtHomeScore.setText(String.valueOf(partido.getPuntosLocal()));
        txtAwayScore.setText(String.valueOf(partido.getPuntosVisitante()));
    }

    private void cargarVista(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            javafx.scene.Node node = lblFormTitle.getScene().getRoot();
            
            StackPane contentPane = buscarContentPane(node);
            
            if (contentPane != null) {
                root.setOpacity(0);
                contentPane.getChildren().setAll(root);
                
                javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(
                    javafx.util.Duration.millis(200), root);
                fade.setToValue(1);
                fade.play();
            } else {
                javafx.stage.Stage stage = (javafx.stage.Stage) lblFormTitle.getScene().getWindow();
                stage.setScene(new javafx.scene.Scene(root));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StackPane buscarContentPane(javafx.scene.Node node) {
        if (node instanceof StackPane && node.getId() != null && node.getId().equals("contentPane")) {
            return (StackPane) node;
        }
        if (node instanceof Parent) {
            for (javafx.scene.Node child : ((Parent) node).getChildrenUnmodifiable()) {
                StackPane found = buscarContentPane(child);
                if (found != null) return found;
            }
        }
        return null;
    }

    @FXML
    private void handleBack() {
        cargarVista("/ui/admin/MatchManagementView.fxml");
    }

    @FXML
    private void handleCancel() {
        handleBack();
    }

    @FXML
    private void handleSave() {
        // Validar campos
        if (comboHomeTeam.getValue() == null) {
            mostrarError("Por favor, selecciona el equipo local.");
            return;
        }

        if (comboAwayTeam.getValue() == null) {
            mostrarError("Por favor, selecciona el equipo visitante.");
            return;
        }

        if (comboHomeTeam.getValue().getId().equals(comboAwayTeam.getValue().getId())) {
            mostrarError("El equipo local y visitante no pueden ser el mismo.");
            return;
        }

        if (dateMatch.getValue() == null) {
            mostrarError("Por favor, selecciona la fecha del partido.");
            return;
        }

        // Crear o actualizar partido
        Partido partido;
        if (esEdicion && partidoEditando != null) {
            partido = partidoEditando;
        } else {
            partido = new Partido();
        }

        partido.setEquipoLocal(comboHomeTeam.getValue());
        partido.setEquipoVisitante(comboAwayTeam.getValue());
        partido.setFecha(dateMatch.getValue());
        partido.setEstado(comboStatus.getValue());

        // Puntos (solo si están ingresados)
        try {
            if (!txtHomeScore.getText().isEmpty()) {
                partido.setPuntosLocal(Integer.parseInt(txtHomeScore.getText()));
            }
            if (!txtAwayScore.getText().isEmpty()) {
                partido.setPuntosVisitante(Integer.parseInt(txtAwayScore.getText()));
            }
        } catch (NumberFormatException e) {
            mostrarError("Los puntos deben ser números válidos.");
            return;
        }

        // Guardar partido
        Partido partidoGuardado = partidoService.crearPartido(partido);
        if (partidoGuardado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText(esEdicion ? "Partido actualizado correctamente." : "Partido creado correctamente.");
            alert.showAndWait();

            handleBack();
        } else {
            mostrarError("Error al guardar el partido. Por favor, intenta nuevamente.");
        }
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
        lblError.setManaged(true);
    }
}
