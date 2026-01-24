import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/ui/layout/MainLayout.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root, 1920, 1080);  // <-- Definir tamaño aquí

        // 👉 AQUÍ se añade el CSS
        scene.getStylesheets().add(
            getClass().getResource("/resources/styles/main.css").toExternalForm()
        );

        stage.setTitle("NBA Predictor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
