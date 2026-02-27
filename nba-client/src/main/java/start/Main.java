package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static {
        // Fuerza el escalado a 1.0 para que no se vea gigante en portátiles
        System.setProperty("glass.win.uiScale", "1.0");;
    }

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
