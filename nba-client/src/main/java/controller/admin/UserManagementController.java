package controller.admin;

import java.io.IOException;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.User;
import service.UsuarioService;

public class UserManagementController {

    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> comboRoleFilter;
    @FXML
    private ComboBox<String> comboStatusFilter;
    @FXML
    private TableView<User> tableUsers;

    @FXML
    private Label lblSelectedCount;
    @FXML
    private Label lblTotalUsers;
    @FXML
    private Label lblActiveUsers;
    @FXML
    private Label lblAdmins;

    private UsuarioService usuarioService;
    private ObservableList<User> usuariosList;
    private FilteredList<User> filteredUsuarios;

    @FXML
    private void initialize() {
        usuarioService = new UsuarioService();

        comboRoleFilter.getItems().addAll(
                "Todos",
                "USER",
                "ADMIN");
        comboRoleFilter.setValue("Todos");

        comboStatusFilter.getItems().addAll(
                "Todos",
                "Activo",
                "Inactivo",
                "Baneado");
        comboStatusFilter.setValue("Todos");

        configurarTabla();
        cargarUsuarios();

        // Listeners para filtros
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        comboRoleFilter.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        comboStatusFilter.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        // Listener para selección
        tableUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            actualizarContadorSeleccionados();
        });
    }

    private void configurarTabla() {
        TableColumn<User, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);

        TableColumn<User, String> colUsername = new TableColumn<>("Usuario");
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUsername.setPrefWidth(150);

        TableColumn<User, String> colEmail = new TableColumn<>("Correo");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(200);

        TableColumn<User, String> colRole = new TableColumn<>("Rol");
        colRole.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colRole.setPrefWidth(120);

        TableColumn<User, Integer> colPoints = new TableColumn<>("Puntos");
        colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        colPoints.setPrefWidth(100);

        TableColumn<User, String> colStatus = new TableColumn<>("Estado");
        colStatus.setCellValueFactory(cell -> {
            // Por ahora, todos están activos (puedes extender el modelo User)
            return new javafx.beans.property.SimpleStringProperty("Activo");
        });
        colStatus.setPrefWidth(100);

        tableUsers.getColumns().setAll(colId, colUsername, colEmail, colRole, colPoints, colStatus);
    }

    private void cargarUsuarios() {
        List<User> usuarios = usuarioService.listarUsuarios();
        usuariosList = FXCollections.observableArrayList(usuarios);
        filteredUsuarios = new FilteredList<>(usuariosList);
        tableUsers.setItems(filteredUsuarios);

        actualizarEstadisticas();
        actualizarContadorSeleccionados();
    }

    private void aplicarFiltros() {
        filteredUsuarios.setPredicate(usuario -> {
            // Filtro de búsqueda
            String searchText = txtSearch.getText().toLowerCase();
            if (!searchText.isEmpty()) {
                if (!usuario.getUsername().toLowerCase().contains(searchText) &&
                    (usuario.getEmail() == null || !usuario.getEmail().toLowerCase().contains(searchText))) {
                    return false;
                }
            }

            // Filtro de rol
            String rolFilter = comboRoleFilter.getValue();
            if (!"Todos".equals(rolFilter)) {
                String rolUsuario = usuario.getRol() != null ? usuario.getRol() : "USER";
                if (!rolUsuario.equalsIgnoreCase(rolFilter)) {
                    return false;
                }
            }

            // Filtro de estado (por ahora todos están activos)
            String statusFilter = comboStatusFilter.getValue();
            if (!"Todos".equals(statusFilter) && !"Activo".equals(statusFilter)) {
                return false; // Por ahora solo mostramos activos
            }

            return true;
        });

        actualizarContadorSeleccionados();
    }

    private void actualizarEstadisticas() {
        lblTotalUsers.setText(String.valueOf(usuariosList.size()));
        
        long activos = usuariosList.stream()
                .count(); // Por ahora todos están activos
        lblActiveUsers.setText(String.valueOf(activos));

        long admins = usuariosList.stream()
                .filter(u -> u.isAdmin())
                .count();
        lblAdmins.setText(String.valueOf(admins));
    }

    private void actualizarContadorSeleccionados() {
        int seleccionados = tableUsers.getSelectionModel().getSelectedItems().size();
        lblSelectedCount.setText(seleccionados + " usuario(s) seleccionado(s)");
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
    private void handleRefresh() {
        cargarUsuarios();
    }

    @FXML
    private void handleEditSelected() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Por favor, selecciona un usuario para editar.");
            return;
        }

        // Crear diálogo de edición
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Editar Usuario");
        dialog.setHeaderText("Editar información del usuario: " + selected.getUsername());

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField txtUsername = new TextField(selected.getUsername());
        TextField txtEmail = new TextField(selected.getEmail());
        ComboBox<String> comboRol = new ComboBox<>(FXCollections.observableArrayList("USER", "ADMIN"));
        comboRol.setValue(selected.getRol() != null ? selected.getRol() : "USER");

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(txtUsername, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(txtEmail, 1, 1);
        grid.add(new Label("Rol:"), 0, 2);
        grid.add(comboRol, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                selected.setUsername(txtUsername.getText());
                selected.setEmail(txtEmail.getText());
                selected.setRol(comboRol.getValue());
                return selected;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(usuarioEditado -> {
            if (usuarioService.actualizarUsuario(usuarioEditado)) {
                mostrarAlerta("Usuario actualizado correctamente.", Alert.AlertType.INFORMATION);
                cargarUsuarios();
            } else {
                mostrarAlerta("Error al actualizar el usuario.", Alert.AlertType.ERROR);
            }
        });
    }

    @FXML
    private void handleChangeRole() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Por favor, selecciona un usuario para cambiar su rol.");
            return;
        }

        String nuevoRol = selected.isAdmin() ? "USER" : "ADMIN";
        selected.setRol(nuevoRol);

        if (usuarioService.actualizarUsuario(selected)) {
            mostrarAlerta("Rol actualizado correctamente.", Alert.AlertType.INFORMATION);
            cargarUsuarios();
        } else {
            mostrarAlerta("Error al actualizar el rol.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDeactivateSelected() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Por favor, selecciona un usuario para desactivar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar desactivación");
        confirmacion.setHeaderText("¿Estás seguro de desactivar al usuario " + selected.getUsername() + "?");
        confirmacion.setContentText("Esta acción puede revertirse más tarde.");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // TODO: Implementar lógica de desactivación cuando el backend lo soporte
                mostrarAlerta("Funcionalidad de desactivación pendiente de implementar en el backend.", 
                             Alert.AlertType.INFORMATION);
            }
        });
    }

    @FXML
    private void handleActivateSelected() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Por favor, selecciona un usuario para activar.");
            return;
        }

        // TODO: Implementar lógica de activación cuando el backend lo soporte
        mostrarAlerta("Funcionalidad de activación pendiente de implementar en el backend.", 
                     Alert.AlertType.INFORMATION);
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
