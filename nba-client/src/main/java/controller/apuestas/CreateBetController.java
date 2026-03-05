package controller.apuestas;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Apuesta;
import model.Partido;
import model.User;
import service.ApuestaService;
import service.PartidoService;
import session.Session;

import java.util.List;
import java.util.stream.Collectors;

public class CreateBetController {

    @FXML
    private ComboBox<Partido> comboPartido;
    @FXML
    private ComboBox<String> comboPrediccion;
    @FXML
    private TextField txtPuntos;
    @FXML
    private Label lblError;
    @FXML
    private Label lblPuntosDisponibles;
    @FXML
    private Label lblCuota;
    @FXML
    private Label lblGananciaPotencial;

    private final PartidoService partidoService = new PartidoService();
    private final ApuestaService apuestaService = new ApuestaService();

    @FXML
    private void initialize() {
        comboPrediccion.getItems().addAll("LOCAL", "VISITANTE");

        // Configurar cell factory para mostrar partidos
        comboPartido.setCellFactory(param -> new javafx.scene.control.ListCell<Partido>() {
            @Override
            protected void updateItem(Partido item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (item.getEquipoLocal() != null && item.getEquipoVisitante() != null) {
                        setText(item.getEquipoLocal().getNombre()
                                + " vs "
                                + item.getEquipoVisitante().getNombre()
                                + (item.getFecha() != null ? " (" + item.getFecha() + ")" : ""));
                    } else {
                        setText("Partido sin equipos");
                    }
                }
            }
        });

        comboPartido.setButtonCell(new javafx.scene.control.ListCell<Partido>() {
            @Override
            protected void updateItem(Partido item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (item.getEquipoLocal() != null && item.getEquipoVisitante() != null) {
                        setText(item.getEquipoLocal().getNombre()
                                + " vs "
                                + item.getEquipoVisitante().getNombre());
                    } else {
                        setText("Seleccionar partido");
                    }
                }
            }
        });

        // Mostrar puntos disponibles
        User usuario = Session.getCurrentUser();
        if (usuario != null) {
            lblPuntosDisponibles.setText("Puntos disponibles: " + usuario.getPoints());
        }

        // Listeners para actualizar cuota y ganancia potencial
        comboPartido.valueProperty().addListener((obs, oldVal, newVal) -> actualizarCuota());
        comboPrediccion.valueProperty().addListener((obs, oldVal, newVal) -> actualizarCuota());
        txtPuntos.textProperty().addListener((obs, oldVal, newVal) -> actualizarGananciaPotencial());

        // Cargar partidos disponibles
        cargarPartidos();
    }

    private void actualizarCuota() {
        Partido partido = comboPartido.getValue();
        String prediccion = comboPrediccion.getValue();

        if (partido != null && prediccion != null) {
            // Calcular cuota (misma lógica que el backend)
            double cuota = calcularCuota(partido, prediccion);
            lblCuota.setText(String.format("Cuota: %.2f", cuota));
            lblCuota.setVisible(true);
            actualizarGananciaPotencial();
        } else {
            lblCuota.setVisible(false);
            lblGananciaPotencial.setVisible(false);
        }
    }

    private void actualizarGananciaPotencial() {
        if (lblCuota == null || !lblCuota.isVisible()) {
            return;
        }

        try {
            String puntosTexto = txtPuntos.getText().trim();
            if (puntosTexto.isEmpty()) {
                lblGananciaPotencial.setVisible(false);
                return;
            }

            int puntos = Integer.parseInt(puntosTexto);
            if (puntos <= 0) {
                lblGananciaPotencial.setVisible(false);
                return;
            }

            // Extraer cuota del label
            String cuotaTexto = lblCuota.getText().replace("Cuota: ", "");
            double cuota = Double.parseDouble(cuotaTexto);

            double gananciaPotencial = puntos * cuota;
            lblGananciaPotencial.setText(String.format("Ganancia potencial: %.0f puntos", gananciaPotencial));
            lblGananciaPotencial.setVisible(true);
        } catch (NumberFormatException e) {
            lblGananciaPotencial.setVisible(false);
        }
    }

    private double calcularCuota(Partido partido, String prediccion) {
        // Misma lógica que el backend para mantener consistencia
        if (partido == null || partido.getEquipoLocal() == null || partido.getEquipoVisitante() == null) {
            return 2.0;
        }

        double variacion = 0.0;
        String nombreLocal = partido.getEquipoLocal().getNombre();
        String nombreVisitante = partido.getEquipoVisitante().getNombre();

        // Validar que los nombres no sean null
        if (nombreLocal == null)
            nombreLocal = "EquipoLocal";
        if (nombreVisitante == null)
            nombreVisitante = "EquipoVisitante";

        int hashLocal = nombreLocal.hashCode();
        int hashVisitante = nombreVisitante.hashCode();

        double factorLocal = (hashLocal % 60 - 30) / 100.0;
        double factorVisitante = (hashVisitante % 60 - 30) / 100.0;

        if ("LOCAL".equalsIgnoreCase(prediccion)) {
            variacion = -factorLocal;
        } else {
            variacion = -factorVisitante;
        }

        double cuotaBase = 1.9;
        double cuota = cuotaBase + variacion;
        cuota = Math.max(1.5, Math.min(5.0, cuota));

        return Math.round(cuota * 100.0) / 100.0;
    }

    private void cargarPartidos() {
        try {
            List<Partido> todosLosPartidos = partidoService.listarPartidos();

            // Filtrar solo partidos programados o en curso (no finalizados)
            List<Partido> partidosDisponibles = todosLosPartidos.stream()
                    .filter(p -> {
                        String estado = p.getEstado();
                        if (estado == null)
                            return true;

                        estado = estado.trim().toLowerCase();

                        return estado.equals("programado") ||
                                estado.equals("en curso") ||
                                estado.equals("encurso") ||
                                estado.equals("scheduled") ||
                                estado.equals("pendiente");
                    })

                    .collect(Collectors.toList());

            comboPartido.setItems(FXCollections.observableArrayList(partidosDisponibles));

            if (partidosDisponibles.isEmpty()) {
                mostrarError("No hay partidos disponibles para apostar en este momento.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar los partidos: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {

        // Limpiar error previo
        ocultarError();

        // Validaciones
        if (comboPartido.getValue() == null) {
            mostrarError("Por favor, selecciona un partido.");
            return;
        }

        if (comboPrediccion.getValue() == null) {
            mostrarError("Por favor, selecciona una predicción (LOCAL o VISITANTE).");
            return;
        }

        String puntosTexto = txtPuntos.getText().trim();
        if (puntosTexto.isEmpty()) {
            mostrarError("Por favor, ingresa la cantidad de puntos a apostar.");
            return;
        }

        int puntos;
        try {
            puntos = Integer.parseInt(puntosTexto);
        } catch (NumberFormatException e) {
            mostrarError("La cantidad de puntos debe ser un número válido.");
            return;
        }

        if (puntos <= 0) {
            mostrarError("La cantidad de puntos debe ser mayor a 0.");
            return;
        }

        // Verificar puntos disponibles
        User usuario = Session.getCurrentUser();
        if (usuario == null) {
            mostrarError("Error: No hay usuario en sesión.");
            return;
        }

        if (usuario.getPoints() < puntos) {
            mostrarError("No tienes suficientes puntos. Disponibles: " + usuario.getPoints());
            return;
        }

        // Crear apuesta
        try {
            Partido partido = comboPartido.getValue();
            String prediccion = comboPrediccion.getValue();

            // Calcular cuota antes de crear la apuesta
            double cuota = calcularCuota(partido, prediccion);

            Apuesta apuesta = new Apuesta();
            Partido p = new Partido();
            p.setId(partido.getId());
            apuesta.setPartido(p);
            apuesta.setPrediccion(prediccion);
            apuesta.setPuntosApostados(puntos);
            apuesta.setCuota(cuota); // Asignar cuota calculada
            apuesta.setUsuario(usuario); // Asignar usuario

            System.out.println("Enviando apuesta:");
            System.out.println("Partido: " + partido.getId());
            System.out.println("Predicción: " + prediccion);
            System.out.println("Puntos: " + puntos);
            System.out.println("Cuota: " + cuota);
            System.out.println("Usuario: " + usuario.getId());

            apuestaService.crearApuesta(apuesta);

            // Mostrar mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Apuesta Creada");
            alert.setHeaderText(null);
            alert.setContentText("¡Apuesta creada exitosamente!");
            alert.showAndWait();

            cerrarVentana();

        } catch (RuntimeException e) {
            mostrarError("Error al crear la apuesta: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error inesperado al crear la apuesta.");
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

    private void mostrarError(String mensaje) {
        if (lblError != null) {
            lblError.setText(mensaje);
            lblError.setVisible(true);
            lblError.setManaged(true);
            lblError.setStyle("-fx-text-fill: red;");
        } else {
            // Si no hay label de error, mostrar alerta
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
    }

    private void ocultarError() {
        if (lblError != null) {
            lblError.setVisible(false);
            lblError.setManaged(false);
        }
    }
}