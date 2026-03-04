package controller.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.Partido;
import model.User;
import service.ApuestaService;
import service.EquipoApiService;
import service.PartidoService;
import service.UsuarioService;

public class AdminDashboardController {

    @FXML
    private Label lblTotalUsers;

    @FXML
    private Label lblActiveMatches;

    @FXML
    private Label lblTotalBets;

    @FXML
    private Label lblTotalTeams;

    @FXML
    private TableView<Partido> tablePendingMatches;

    @FXML
    private TableView<ActivityItem> tableRecentActivity;

    private PartidoService partidoService;
    private UsuarioService usuarioService;
    private ApuestaService apuestaService;
    private EquipoApiService equipoService;

    @FXML
    private void initialize() {
        partidoService = new PartidoService();
        usuarioService = new UsuarioService();
        apuestaService = new ApuestaService();
        equipoService = new EquipoApiService();

        configurarTablas();
        cargarDatos();
    }

    private void configurarTablas() {
        // Configurar tabla de partidos pendientes
        TableColumn<Partido, LocalDate> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFecha.setPrefWidth(150);

        TableColumn<Partido, String> colLocal = new TableColumn<>("Equipo Local");
        colLocal.setCellValueFactory(cell -> {
            if (cell.getValue().getEquipoLocal() != null && cell.getValue().getEquipoLocal().getNombre() != null) {
                return new javafx.beans.property.SimpleStringProperty(cell.getValue().getEquipoLocal().getNombre());
            }
            return new javafx.beans.property.SimpleStringProperty("Equipo Local");
        });
        colLocal.setPrefWidth(200);

        TableColumn<Partido, String> colVisitante = new TableColumn<>("Equipo Visitante");
        colVisitante.setCellValueFactory(cell -> {
            if (cell.getValue().getEquipoVisitante() != null && cell.getValue().getEquipoVisitante().getNombre() != null) {
                return new javafx.beans.property.SimpleStringProperty(cell.getValue().getEquipoVisitante().getNombre());
            }
            return new javafx.beans.property.SimpleStringProperty("Equipo Visitante");
        });
        colVisitante.setPrefWidth(200);

        TableColumn<Partido, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cell -> {
            String estado = cell.getValue().getEstado();
            return new javafx.beans.property.SimpleStringProperty(estado != null ? estado : "Programado");
        });
        colEstado.setPrefWidth(100);

        tablePendingMatches.getColumns().setAll(colFecha, colLocal, colVisitante, colEstado);

        // Configurar tabla de actividad reciente
        TableColumn<ActivityItem, String> colFechaAct = new TableColumn<>("Fecha");
        colFechaAct.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFechaAct.setPrefWidth(150);

        TableColumn<ActivityItem, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTipo.setPrefWidth(150);

        TableColumn<ActivityItem, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setPrefWidth(300);

        TableColumn<ActivityItem, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colUsuario.setPrefWidth(150);

        tableRecentActivity.getColumns().setAll(colFechaAct, colTipo, colDescripcion, colUsuario);
    }

    private void cargarDatos() {
        try {
            // Cargar estadísticas
            List<User> usuarios = usuarioService.listarUsuarios();
            lblTotalUsers.setText(String.valueOf(usuarios.size()));

            List<Partido> partidos = partidoService.listarPartidos();
            long partidosActivos = partidos.stream()
                    .filter(p -> "En curso".equals(p.getEstado()) || "Programado".equals(p.getEstado()))
                    .count();
            lblActiveMatches.setText(String.valueOf(partidosActivos));

            // Contar apuestas (aproximación - necesitarías un endpoint específico)
            lblTotalBets.setText("0"); // TODO: Implementar cuando haya endpoint

            List<model.Equipo> equipos = equipoService.obtenerEquipos();
            lblTotalTeams.setText(String.valueOf(equipos.size()));

            // Cargar partidos pendientes (programados)
            List<Partido> partidosPendientes = partidos.stream()
                    .filter(p -> "Programado".equals(p.getEstado()) || p.getEstado() == null)
                    .limit(10)
                    .collect(Collectors.toList());
            tablePendingMatches.getItems().setAll(partidosPendientes);

            // Cargar actividad reciente (simulado)
            tableRecentActivity.getItems().add(new ActivityItem(
                    LocalDate.now().toString(),
                    "Sistema",
                    "Panel de administración iniciado",
                    "Admin"
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarVista(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            javafx.scene.Node node = lblTotalUsers.getScene().getRoot();
            
            // Buscar el StackPane contentPane en la jerarquía
            StackPane contentPane = buscarContentPane(node);
            
            if (contentPane != null) {
                root.setOpacity(0);
                contentPane.getChildren().setAll(root);
                
                javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(
                    javafx.util.Duration.millis(200), root);
                fade.setToValue(1);
                fade.play();
            } else {
                // Si no está en MainLayout, cambiar la escena completa
                javafx.stage.Stage stage = (javafx.stage.Stage) lblTotalUsers.getScene().getWindow();
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
    private void handleManageUsers() {
        cargarVista("/ui/admin/UserManagementView.fxml");
    }

    @FXML
    private void handleManageMatches() {
        cargarVista("/ui/admin/MatchManagementView.fxml");
    }

    @FXML
    private void handleCreateMatch() {
        cargarVista("/ui/admin/MatchFormView.fxml");
    }

    @FXML
    private void handleRefreshData() {
        cargarDatos();
    }

    // Clase auxiliar para la tabla de actividad
    public static class ActivityItem {
        private String fecha;
        private String tipo;
        private String descripcion;
        private String usuario;

        public ActivityItem(String fecha, String tipo, String descripcion, String usuario) {
            this.fecha = fecha;
            this.tipo = tipo;
            this.descripcion = descripcion;
            this.usuario = usuario;
        }

        public String getFecha() { return fecha; }
        public String getTipo() { return tipo; }
        public String getDescripcion() { return descripcion; }
        public String getUsuario() { return usuario; }
    }
}
