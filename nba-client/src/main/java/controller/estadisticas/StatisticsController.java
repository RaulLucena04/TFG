package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StatisticsController {

    @FXML
    private TextField txtSearchTeam;

    @FXML
    private TextField txtSearchPlayer;

    private static final String CSS_PATH = "/styles/main.css";

    @FXML
    private void handleSearchTeam() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/estadisticas/TeamsStatsView.fxml"));
            Parent root = loader.load();

            TeamsStatsController controller = loader.getController();
            controller.searchTeam(txtSearchTeam.getText());

            Stage stage = (Stage) txtSearchTeam.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewAllTeams() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/estadisticas/TeamsStatsView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtSearchTeam.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewAllPlayers() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/estadisticas/PlayersStatsView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtSearchTeam.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchPlayer() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/estadisticas/PlayersStatsView.fxml"));
            Parent root = loader.load();

            PlayersStatsController controller = loader.getController();
            controller.handleSearch(); // ← corregido

            Stage stage = (Stage) txtSearchPlayer.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
