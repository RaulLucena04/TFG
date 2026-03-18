package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Config;

/**
 * Clase principal de la aplicación de escritorio NBA Predictor.
 * 
 * <p>Esta aplicación JavaFX proporciona una interfaz gráfica para el sistema
 * de predicciones de partidos de la NBA. Inicia con la pantalla de login.
 * 
 * @author TFG
 * @version 1.0
 */
public class Main extends Application {

    static {
        System.setProperty("glass.win.uiScale", "1.0");
    }

    /**
     * Inicia la aplicación JavaFX cargando la pantalla de login.
     * 
     * @param stage el escenario principal de la aplicación
     * @throws Exception si hay un error al cargar la interfaz
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/ui/auth/login/LoginView.fxml")
        );

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("/styles/main.css").toExternalForm()
        );

        stage.setTitle("NBA Predictor");
        stage.setScene(scene);
        stage.show();
        
        // Mostrar diálogo de configuración de servidor después de que la ventana esté visible
        Config.promptServerUrl();
    }

    /**
     * Método principal que inicia la aplicación JavaFX.
     * 
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}
