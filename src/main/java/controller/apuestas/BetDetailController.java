package controller.apuestas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BetDetailController {

    // Información de la apuesta
    @FXML
    private Label lblBetId;
    @FXML
    private Label lblBetDate;
    @FXML
    private Label lblBetStatus;
    @FXML
    private Label lblBetType;
    @FXML
    private Label lblPrediction;
    @FXML
    private Label lblBetAmount;
    @FXML
    private Label lblOdds;
    @FXML
    private Label lblPotentialWin;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblProfitLoss;

    // Información del partido
    @FXML
    private Label lblHomeTeam;
    @FXML
    private Label lblHomeScore;
    @FXML
    private Label lblAwayTeam;
    @FXML
    private Label lblAwayScore;
    @FXML
    private Label lblMatchDate;

    @FXML
    private void initialize() {
        // Valores de prueba para que se vea algo
        lblBetId.setText("12345");
        lblBetDate.setText("20/05/2026");
        lblBetStatus.setText("En curso");
        lblBetType.setText("Resultado Final");
        lblPrediction.setText("Gana Local");
        lblBetAmount.setText("50 €");
        lblOdds.setText("2.10");
        lblPotentialWin.setText("105 €");
        lblResult.setText("-");
        lblProfitLoss.setText("-");

        lblHomeTeam.setText("Equipo A");
        lblHomeScore.setText("1");
        lblAwayTeam.setText("Equipo B");
        lblAwayScore.setText("0");
        lblMatchDate.setText("21/05/2026 - 18:00");
    }

    @FXML
    private void handleBack() {
        // navegación atrás
    }
}
