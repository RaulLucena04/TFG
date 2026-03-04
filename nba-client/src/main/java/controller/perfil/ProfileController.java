package controller.perfil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Apuesta;
import model.Partido;
import model.User;
import service.ApuestaService;
import service.UsuarioService;
import session.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileController {

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblRegistrationDate;

    @FXML
    private Label lblTotalPoints;

    @FXML
    private Label lblTotalBets;

    @FXML
    private Label lblWinRate;

    @FXML
    private Label lblRanking;

    @FXML
    private Pane chartContainer;

    @FXML
    private TableView<ApuestaRow> tableRecentBets;

    private ApuestaService apuestaService;
    private UsuarioService usuarioService;

    @FXML
    private void initialize() {
        apuestaService = new ApuestaService();
        usuarioService = new UsuarioService();
        
        configurarTabla();
        cargarDatosUsuario();
        cargarEstadisticas();
        cargarApuestasRecientes();
    }

    private void configurarTabla() {
        TableColumn<ApuestaRow, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFecha.setPrefWidth(150);

        TableColumn<ApuestaRow, String> colPartido = new TableColumn<>("Partido");
        colPartido.setCellValueFactory(new PropertyValueFactory<>("partido"));
        colPartido.setPrefWidth(250);

        TableColumn<ApuestaRow, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTipo.setPrefWidth(120);

        TableColumn<ApuestaRow, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCantidad.setPrefWidth(100);

        TableColumn<ApuestaRow, String> colResultado = new TableColumn<>("Resultado");
        colResultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        colResultado.setPrefWidth(100);

        TableColumn<ApuestaRow, String> colGanancia = new TableColumn<>("Ganancia/Pérdida");
        colGanancia.setCellValueFactory(new PropertyValueFactory<>("ganancia"));
        colGanancia.setPrefWidth(130);

        tableRecentBets.getColumns().setAll(colFecha, colPartido, colTipo, colCantidad, colResultado, colGanancia);
    }

    private void cargarDatosUsuario() {
        User usuario = Session.getCurrentUser();
        if (usuario == null) {
            return;
        }

        lblUsername.setText(usuario.getUsername());
        lblEmail.setText(usuario.getEmail() != null ? usuario.getEmail() : "No disponible");
        
        // Fecha de registro (simulada, ya que no está en el modelo)
        lblRegistrationDate.setText("N/A");
    }

    private void cargarEstadisticas() {
        User usuario = Session.getCurrentUser();
        if (usuario == null) {
            return;
        }

        Long userId = usuario.getId();
        List<Apuesta> apuestas = apuestaService.obtenerApuestasUsuario(userId);

        // Puntos totales
        lblTotalPoints.setText(String.valueOf(usuario.getPoints()));

        // Total de apuestas
        int totalApuestas = apuestas.size();
        lblTotalBets.setText(String.valueOf(totalApuestas));

        // Tasa de aciertos
        long apuestasFinalizadas = apuestas.stream()
                .filter(a -> a.isGanada() || a.isPerdida())
                .count();
        
        long apuestasGanadas = apuestas.stream()
                .filter(Apuesta::isGanada)
                .count();

        double winRate = apuestasFinalizadas > 0
                ? (apuestasGanadas * 100.0) / apuestasFinalizadas
                : 0.0;
        
        lblWinRate.setText(String.format("%.1f%%", winRate));

        // Ranking (necesitamos obtener todos los usuarios y calcular posición)
        calcularRanking(usuario);
    }

    private void calcularRanking(User usuario) {
        try {
            List<User> usuarios = usuarioService.listarUsuarios();
            
            // Ordenar por puntos descendente
            usuarios.sort(Comparator.comparingInt(User::getPoints).reversed());
            
            int posicion = 1;
            for (User u : usuarios) {
                if (u.getId().equals(usuario.getId())) {
                    lblRanking.setText("#" + posicion);
                    break;
                }
                posicion++;
            }
        } catch (Exception e) {
            lblRanking.setText("#?");
        }
    }

    private void cargarApuestasRecientes() {
        User usuario = Session.getCurrentUser();
        if (usuario == null) {
            return;
        }

        Long userId = usuario.getId();
        List<Apuesta> apuestas = apuestaService.obtenerApuestasUsuario(userId);

        // Ordenar por fecha más reciente (si hay fecha en el modelo)
        // Por ahora, tomar las últimas 10
        List<Apuesta> apuestasRecientes = apuestas.stream()
                .limit(10)
                .collect(Collectors.toList());

        ObservableList<ApuestaRow> rows = FXCollections.observableArrayList();
        
        for (Apuesta apuesta : apuestasRecientes) {
            Partido partido = apuesta.getPartido();
            String partidoStr = "N/A";
            String fechaStr = "N/A";
            
            if (partido != null) {
                if (partido.getEquipoLocal() != null && 
                    partido.getEquipoVisitante() != null &&
                    partido.getEquipoLocal().getNombre() != null &&
                    partido.getEquipoVisitante().getNombre() != null) {
                    partidoStr = partido.getEquipoLocal().getNombre() + " vs " + 
                                partido.getEquipoVisitante().getNombre();
                }
                if (partido.getFecha() != null) {
                    fechaStr = partido.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }
            }

            String tipo = apuesta.getPrediccion() != null ? apuesta.getPrediccion() : "N/A";
            int cantidad = apuesta.getPuntosApostados();
            
            String resultado = "Pendiente";
            String ganancia = "-";
            
            if (apuesta.isGanada()) {
                resultado = "Ganada";
                Double cuota = apuesta.getCuota();
                if (cuota != null && cuota > 0) {
                    double gananciaCalculada = cantidad * cuota;
                    ganancia = "+" + String.format("%.0f", gananciaCalculada);
                } else {
                    ganancia = "+" + (cantidad * 2); // Fallback si no hay cuota
                }
            } else if (apuesta.isPerdida()) {
                resultado = "Perdida";
                ganancia = "-" + cantidad;
            }

            rows.add(new ApuestaRow(fechaStr, partidoStr, tipo, cantidad, resultado, ganancia));
        }

        tableRecentBets.setItems(rows);
    }

    @FXML
    private void handleEditProfile() {
        User usuario = Session.getCurrentUser();
        if (usuario == null) {
            return;
        }

        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Editar Perfil");
        dialog.setHeaderText("Edita tu información personal");

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField txtUsername = new TextField(usuario.getUsername());
        TextField txtEmail = new TextField(usuario.getEmail());

        grid.add(new Label("Nombre de usuario:"), 0, 0);
        grid.add(txtUsername, 1, 0);
        grid.add(new Label("Correo electrónico:"), 0, 1);
        grid.add(txtEmail, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                usuario.setUsername(txtUsername.getText());
                usuario.setEmail(txtEmail.getText());
                return usuario;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(usuarioEditado -> {
            if (usuarioService.actualizarUsuario(usuarioEditado)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Perfil actualizado correctamente.");
                alert.showAndWait();
                
                // Actualizar sesión
                Session.setCurrentUser(usuarioEditado);
                cargarDatosUsuario();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al actualizar el perfil. Por favor, intenta nuevamente.");
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void handleChangePassword() {
        User usuario = Session.getCurrentUser();
        if (usuario == null) {
            return;
        }

        Dialog<javafx.util.Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Cambiar Contraseña");
        dialog.setHeaderText("Ingresa tu nueva contraseña");

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Nueva contraseña");
        PasswordField txtConfirmPassword = new PasswordField();
        txtConfirmPassword.setPromptText("Confirmar contraseña");

        grid.add(new Label("Nueva contraseña:"), 0, 0);
        grid.add(txtPassword, 1, 0);
        grid.add(new Label("Confirmar contraseña:"), 0, 1);
        grid.add(txtConfirmPassword, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String password = txtPassword.getText();
                String confirmPassword = txtConfirmPassword.getText();
                
                if (password.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("La contraseña no puede estar vacía.");
                    alert.showAndWait();
                    return null;
                }
                
                // Validar longitud mínima de contraseña (6 caracteres)
                if (password.length() < 6) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("La contraseña debe tener al menos 6 caracteres.");
                    alert.showAndWait();
                    return null;
                }
                
                if (!password.equals(confirmPassword)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Las contraseñas no coinciden.");
                    alert.showAndWait();
                    return null;
                }
                
                return new javafx.util.Pair<>(password, confirmPassword);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (usuarioService.updatePassword(usuario.getId(), result.getKey())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Contraseña actualizada correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al actualizar la contraseña. Por favor, intenta nuevamente.");
                alert.showAndWait();
            }
        });
    }

    // Clase auxiliar para la tabla de apuestas
    public static class ApuestaRow {
        private String fecha;
        private String partido;
        private String tipo;
        private Integer cantidad;
        private String resultado;
        private String ganancia;

        public ApuestaRow(String fecha, String partido, String tipo, Integer cantidad, String resultado, String ganancia) {
            this.fecha = fecha;
            this.partido = partido;
            this.tipo = tipo;
            this.cantidad = cantidad;
            this.resultado = resultado;
            this.ganancia = ganancia;
        }

        public String getFecha() { return fecha; }
        public String getPartido() { return partido; }
        public String getTipo() { return tipo; }
        public Integer getCantidad() { return cantidad; }
        public String getResultado() { return resultado; }
        public String getGanancia() { return ganancia; }
    }
}
