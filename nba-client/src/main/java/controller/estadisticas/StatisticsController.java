package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class StatisticsController {

    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        System.out.println("StatisticsController iniciado");
    }

    @FXML
    private void handleSearchTeam() {
        System.out.println("Botón Buscar Equipo pulsado");
    }

    @FXML
    private void handleViewAllTeams() {
        System.out.println("Botón Ver Todos Equipos pulsado");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/estadisticas/TeamsStatsView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Estadísticas de Equipos");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchPlayer() {
        System.out.println("Botón Buscar Jugador pulsado");
    }

    @FXML
    private void handleViewAllPlayers() {
        System.out.println("Botón Ver Todos Jugadores pulsado");
    }
}