package controller.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.Partido;
import service.PartidoService;

public class MatchManagementController {

    @FXML
    private TextField txtSearch;

    @FXML
    private ComboBox<String> comboStatusFilter;

    @FXML
    private DatePicker dateFilter;

    @FXML
    private TableView<Partido> tableMatches;

    @FXML
    private Label lblSelectedCount;

    private PartidoService partidoService;
    private ObservableList<Partido> partidosList;
    private FilteredList<Partido> filteredPartidos;

    @FXML
    private void initialize() {
        partidoService = new PartidoService();

        comboStatusFilter.getItems().addAll(
            "Todos",
            "Programados",
            "En curso",
            "Finalizados"
        );

        comboStatusFilter.setValue("Todos");

        configurarTabla();
        cargarPartidos();

        // Listeners para filtros
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        comboStatusFilter.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        dateFilter.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        // Listener para selección
        tableMatches.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            actualizarContadorSeleccionados();
        });
    }

    private void configurarTabla() {
        TableColumn<Partido, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);

        TableColumn<Partido, LocalDate> colDate = new TableColumn<>("Fecha");
        colDate.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colDate.setPrefWidth(150);

        TableColumn<Partido, String> colHome = new TableColumn<>("Equipo Local");
        colHome.setCellValueFactory(cell -> {
            if (cell.getValue().getEquipoLocal() != null && cell.getValue().getEquipoLocal().getNombre() != null) {
                return new javafx.beans.property.SimpleStringProperty(cell.getValue().getEquipoLocal().getNombre());
            }
            return new javafx.beans.property.SimpleStringProperty("Equipo Local");
        });
        colHome.setPrefWidth(200);

        TableColumn<Partido, String> colAway = new TableColumn<>("Equipo Visitante");
        colAway.setCellValueFactory(cell -> {
            if (cell.getValue().getEquipoVisitante() != null && cell.getValue().getEquipoVisitante().getNombre() != null) {
                return new javafx.beans.property.SimpleStringProperty(cell.getValue().getEquipoVisitante().getNombre());
            }
            return new javafx.beans.property.SimpleStringProperty("Equipo Visitante");
        });
        colAway.setPrefWidth(200);

        TableColumn<Partido, Integer> colHomeScore = new TableColumn<>("Puntos Local");
        colHomeScore.setCellValueFactory(new PropertyValueFactory<>("puntosLocal"));
        colHomeScore.setPrefWidth(100);

        TableColumn<Partido, Integer> colAwayScore = new TableColumn<>("Puntos Visitante");
        colAwayScore.setCellValueFactory(new PropertyValueFactory<>("puntosVisitante"));
        colAwayScore.setPrefWidth(120);

        TableColumn<Partido, String> colStatus = new TableColumn<>("Estado");
        colStatus.setCellValueFactory(cell -> {
            String estado = cell.getValue().getEstado();
            return new javafx.beans.property.SimpleStringProperty(estado != null ? estado : "Programado");
        });
        colStatus.setPrefWidth(120);

        tableMatches.getColumns().setAll(colId, colDate, colHome, colAway, colHomeScore, colAwayScore, colStatus);
    }

    private void cargarPartidos() {
        List<Partido> partidos = partidoService.listarPartidos();
        partidosList = FXCollections.observableArrayList(partidos);
        filteredPartidos = new FilteredList<>(partidosList);
        tableMatches.setItems(filteredPartidos);

        actualizarContadorSeleccionados();
    }

    private void aplicarFiltros() {
        filteredPartidos.setPredicate(partido -> {
            // Filtro de búsqueda
            String searchText = txtSearch.getText().toLowerCase();
            if (!searchText.isEmpty()) {
                String equipoLocal = (partido.getEquipoLocal() != null && partido.getEquipoLocal().getNombre() != null) ? 
                    partido.getEquipoLocal().getNombre().toLowerCase() : "";
                String equipoVisitante = (partido.getEquipoVisitante() != null && partido.getEquipoVisitante().getNombre() != null) ? 
                    partido.getEquipoVisitante().getNombre().toLowerCase() : "";
                
                if (!equipoLocal.contains(searchText) && !equipoVisitante.contains(searchText)) {
                    return false;
                }
            }

            // Filtro de estado
            String statusFilter = comboStatusFilter.getValue();
            if (!"Todos".equals(statusFilter)) {
                String estado = partido.getEstado() != null ? partido.getEstado() : "Programado";
                if (!estado.equalsIgnoreCase(statusFilter) && 
                    !(statusFilter.equals("Programados") && estado.equals("Programado"))) {
                    return false;
                }
            }

            // Filtro de fecha
            LocalDate fechaFiltro = dateFilter.getValue();
            if (fechaFiltro != null && partido.getFecha() != null) {
                if (!partido.getFecha().equals(fechaFiltro)) {
                    return false;
                }
            }

            return true;
        });

        actualizarContadorSeleccionados();
    }

    private void actualizarContadorSeleccionados() {
        int seleccionados = tableMatches.getSelectionModel().getSelectedItems().size();
        lblSelectedCount.setText(seleccionados + " partido(s) seleccionado(s)");
    }

    private void cargarVista(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            javafx.scene.Node node = txtSearch.getScene().getRoot();
            
            StackPane contentPane = buscarContentPane(node);
            
            if (contentPane != null) {
                root.setOpacity(0);
                contentPane.getChildren().setAll(root);
                
                javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(
                    javafx.util.Duration.millis(200), root);
                fade.setToValue(1);
                fade.play();
            } else {
                javafx.stage.Stage stage = (javafx.stage.Stage) txtSearch.getScene().getWindow();
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
        cargarVista("/ui/admin/AdminDashboardView.fxml");
    }

    @FXML
    private void handleSearch() {
        aplicarFiltros();
    }

    @FXML
    private void handleCreateMatch() {
        cargarVista("/ui/admin/MatchFormView.fxml");
    }

    @FXML
    private void handleRefresh() {
        cargarPartidos();
    }

    @FXML
    private void handleEditSelected() {
        Partido selected = tableMatches.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Por favor, selecciona un partido para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/admin/MatchFormView.fxml"));
            Parent root = loader.load();
            MatchFormController controller = loader.getController();
            controller.cargarPartido(selected);

            javafx.scene.Node node = txtSearch.getScene().getRoot();
            StackPane contentPane = buscarContentPane(node);
            
            if (contentPane != null) {
                root.setOpacity(0);
                contentPane.getChildren().setAll(root);
                
                javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(
                    javafx.util.Duration.millis(200), root);
                fade.setToValue(1);
                fade.play();
            } else {
                javafx.stage.Stage stage = (javafx.stage.Stage) txtSearch.getScene().getWindow();
                stage.setScene(new javafx.scene.Scene(root));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteSelected() {
        Partido selected = tableMatches.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Por favor, selecciona un partido para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar este partido?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // TODO: Implementar eliminación cuando el backend lo soporte
                mostrarAlerta("Funcionalidad de eliminación pendiente de implementar en el backend.", 
                             Alert.AlertType.INFORMATION);
            }
        });
    }

    @FXML
    private void handleFinishSelected() {
        Partido selected = tableMatches.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Por favor, selecciona un partido para finalizar.");
            return;
        }

        if ("Finalizado".equals(selected.getEstado())) {
            mostrarAlerta("Este partido ya está finalizado.", Alert.AlertType.WARNING);
            return;
        }

        // Diálogo para ingresar marcador final
        Dialog<javafx.util.Pair<Integer, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Finalizar Partido");
        dialog.setHeaderText("Ingresa el marcador final del partido");

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField txtPuntosLocal = new TextField();
        txtPuntosLocal.setPromptText("Puntos " + (selected.getEquipoLocal() != null ? selected.getEquipoLocal().getNombre() : "Local"));
        TextField txtPuntosVisitante = new TextField();
        txtPuntosVisitante.setPromptText("Puntos " + (selected.getEquipoVisitante() != null ? selected.getEquipoVisitante().getNombre() : "Visitante"));

        grid.add(new Label("Puntos Local:"), 0, 0);
        grid.add(txtPuntosLocal, 1, 0);
        grid.add(new Label("Puntos Visitante:"), 0, 1);
        grid.add(txtPuntosVisitante, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    int puntosLocal = Integer.parseInt(txtPuntosLocal.getText());
                    int puntosVisitante = Integer.parseInt(txtPuntosVisitante.getText());
                    return new javafx.util.Pair<>(puntosLocal, puntosVisitante);
                } catch (NumberFormatException e) {
                    mostrarAlerta("Por favor, ingresa números válidos para los puntos.", Alert.AlertType.ERROR);
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(resultado -> {
            if (partidoService.finalizarPartido(selected.getId(), resultado.getKey(), resultado.getValue())) {
                mostrarAlerta("Partido finalizado correctamente.", Alert.AlertType.INFORMATION);
                cargarPartidos();
            } else {
                mostrarAlerta("Error al finalizar el partido.", Alert.AlertType.ERROR);
            }
        });
    }

    private void mostrarAlerta(String mensaje) {
        mostrarAlerta(mensaje, Alert.AlertType.WARNING);
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
