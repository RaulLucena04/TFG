package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class StatisticsController {

    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        // Inicialización simple
        System.out.println("StatisticsController iniciado");
    }

    @FXML
    private void handleSearchTeam() {
        System.out.println("Botón Buscar Equipo pulsado");
        // Solo un placeholder, sin lógica aún
    }

    @FXML
    private void handleViewAllTeams() {
        System.out.println("Botón Ver Todos Equipos pulsado");
        // Placeholder sin lógica
    }

    @FXML
    private void handleSearchPlayer() {
        System.out.println("Botón Buscar Jugador pulsado");
        // Placeholder sin lógica
    }

    @FXML
    private void handleViewAllPlayers() {
        System.out.println("Botón Ver Todos Jugadores pulsado");
        // Placeholder sin lógica
    }
}
