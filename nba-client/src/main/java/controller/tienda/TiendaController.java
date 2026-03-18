package controller.tienda;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.TiendaService;
import service.TiendaService.CanjearPuntosResponse;
import session.Session;
import java.util.Locale;

public class TiendaController {

    @FXML
    private Label lblPuntosDisponibles;
    @FXML
    private TextField txtPuntos;
    @FXML
    private TextField txtEmailPayPal;
    @FXML
    private Label lblResultado;

    private final TiendaService tiendaService = new TiendaService();

    @FXML
    public void initialize() {
        actualizarPuntos();
    }

    private void actualizarPuntos() {
        var user = Session.getCurrentUser();
        if (user != null) {
            lblPuntosDisponibles.setText("Puntos disponibles: " + user.getPoints());
        }
    }

    @FXML
    private void handleActualizar() {
        actualizarPuntos();
        lblResultado.setVisible(false);
        lblResultado.setManaged(false);
    }

    @FXML
    private void handleCanjear() {
        var user = Session.getCurrentUser();
        if (user == null || user.getId() == null) {
            mostrarResultado("Inicia sesión para canjear puntos", false);
            return;
        }

        String puntosStr = txtPuntos.getText().trim();
        if (puntosStr.isEmpty()) {
            mostrarResultado("Indica la cantidad de puntos a canjear", false);
            return;
        }

        int puntos;
        try {
            puntos = Integer.parseInt(puntosStr);
        } catch (NumberFormatException e) {
            mostrarResultado("Cantidad de puntos no válida", false);
            return;
        }

        String emailPayPal = txtEmailPayPal.getText().trim();
        if (emailPayPal.isEmpty()) {
            mostrarResultado("Indica tu email de PayPal", false);
            return;
        }

        try {
            CanjearPuntosResponse resp = tiendaService.canjearPuntos(user.getId(), puntos, emailPayPal);
            if (resp.exito) {
                mostrarResultado("¡Canje exitoso! Se han transferido " +
                        String.format(Locale.US, "%.2f", resp.eurosTransferidos) + "€ a tu PayPal. " + resp.mensaje, true);
                
                // Actualizar puntos en el layout
                controller.layout.MainLayoutController mainController = controller.layout.MainLayoutController.getInstance();
                if (mainController != null) {
                    mainController.actualizarPuntos();
                }
                
                actualizarPuntos();
                txtPuntos.clear();
            } else {
                mostrarResultado(resp.mensaje, false);
            }
        } catch (Exception e) {
            mostrarResultado("Error: " + e.getMessage(), false);
        }
    }

    private void mostrarResultado(String texto, boolean exito) {
        lblResultado.setText(texto);
        lblResultado.setStyle(exito ? "-fx-text-fill: #27ae60;" : "-fx-text-fill: #e74c3c;");
        lblResultado.setVisible(true);
        lblResultado.setManaged(true);
    }
}
