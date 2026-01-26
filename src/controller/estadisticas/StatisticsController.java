package controller.estadisticas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.NavigationUtils;
import controller.layout.MainLayoutController;

public class StatisticsController {

    @FXML
    private TabPane tabPane;
    
    @FXML
    private TextField txtSearchTeam;
    
    @FXML
    private TextField txtSearchPlayer;
    
    @FXML
    private ComboBox<String> comboTeamFilter;
    
    @FXML
    private TableView<?> tablePlayers;
    
    @FXML
    private VBox teamsContainer;
    
    @FXML
    private VBox root;

    /**
     * Navega a la vista de detalle de un equipo.
     * @param teamId El ID del equipo a mostrar
     */
    public void navigateToTeamDetail(int teamId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/TeamDetailView.fxml");
            // TODO: Pasar el teamId al controlador de detalle
        }
    }

    /**
     * Navega a la vista de detalle de un equipo (sobrecarga sin parámetros).
     */
    @FXML
    private void navigateToTeamDetail() {
        navigateToTeamDetail(0); // Se puede obtener el ID del contexto
    }

    /**
     * Navega a la vista de detalle de un jugador.
     * @param playerId El ID del jugador a mostrar
     */
    public void navigateToPlayerDetail(int playerId) {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/PlayerDetailView.fxml");
            // TODO: Pasar el playerId al controlador de detalle
        }
    }

    /**
     * Navega a la vista de detalle de un jugador (sobrecarga sin parámetros).
     */
    @FXML
    private void navigateToPlayerDetail() {
        navigateToPlayerDetail(0); // Se puede obtener el ID del contexto
    }

    /**
     * Navega a la vista de estadísticas de equipos.
     */
    @FXML
    private void navigateToTeamsStats() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/TeamsStatsView.fxml");
        }
    }

    /**
     * Navega a la vista de estadísticas de jugadores.
     */
    @FXML
    private void navigateToPlayersStats() {
        StackPane contentPane = getContentPane();
        if (contentPane != null) {
            NavigationUtils.loadView(contentPane, "/ui/estadisticas/PlayersStatsView.fxml");
        }
    }

    @FXML
    private void handleSearchTeam() {
        // TODO: Implementar lógica de búsqueda de equipos
        System.out.println("Buscando equipo: " + txtSearchTeam.getText());
    }

    @FXML
    private void handleSearchPlayer() {
        // TODO: Implementar lógica de búsqueda de jugadores
        System.out.println("Buscando jugador: " + txtSearchPlayer.getText());
    }

    @FXML
    private void handleViewAllTeams() {
        navigateToTeamsStats();
    }

    @FXML
    private void handleViewAllPlayers() {
        navigateToPlayersStats();
    }

    /**
     * Obtiene el StackPane del contenido principal desde MainLayoutController.
     */
    private StackPane getContentPane() {
        MainLayoutController mainController = MainLayoutController.getInstance();
        if (mainController != null) {
            return mainController.getContentPane();
        }
        return NavigationUtils.findContentPane(root);
    }
}
