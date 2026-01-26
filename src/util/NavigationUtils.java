package util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * Clase utilitaria para manejar la navegación entre pantallas en la aplicación.
 * Proporciona métodos estáticos para cargar vistas FXML en el contenedor principal.
 */
public class NavigationUtils {

    /**
     * Carga una vista FXML en el contenedor principal especificado.
     * 
     * @param contentPane El StackPane donde se cargará la vista
     * @param fxmlPath La ruta del archivo FXML (ej: "/ui/partidos/MatchesView.fxml")
     * @return true si la carga fue exitosa, false en caso contrario
     */
    public static boolean loadView(StackPane contentPane, String fxmlPath) {
        try {
            java.net.URL resource = null;
            // Intentar primero con getResource de la clase (como Main.java)
            if (fxmlPath.startsWith("/")) {
                resource = NavigationUtils.class.getResource(fxmlPath);
            }
            // Si no funciona, intentar con ClassLoader del contexto
            if (resource == null) {
                String path = fxmlPath.startsWith("/") ? fxmlPath.substring(1) : fxmlPath;
                resource = Thread.currentThread().getContextClassLoader().getResource(path);
            }
            if (resource == null) {
                System.err.println("No se encontró el archivo FXML: " + fxmlPath);
                return false;
            }
            Parent view = FXMLLoader.load(resource);
            if (view != null) {
                contentPane.getChildren().setAll(view);
                return true;
            } else {
                System.err.println("No se pudo cargar la vista: " + fxmlPath);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Error cargando la vista: " + fxmlPath);
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            System.err.println("No se encontró el archivo FXML: " + fxmlPath + 
                             ". Asegúrate de que el archivo existe en src/ui/");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carga una vista FXML con un controlador personalizado.
     * 
     * @param contentPane El StackPane donde se cargará la vista
     * @param fxmlPath La ruta del archivo FXML
     * @param controller El controlador a usar
     * @return true si la carga fue exitosa, false en caso contrario
     */
    public static boolean loadViewWithController(StackPane contentPane, String fxmlPath, Object controller) {
        try {
            java.net.URL resource = null;
            // Intentar primero con getResource de la clase (como Main.java)
            if (fxmlPath.startsWith("/")) {
                resource = NavigationUtils.class.getResource(fxmlPath);
            }
            // Si no funciona, intentar con ClassLoader del contexto
            if (resource == null) {
                String path = fxmlPath.startsWith("/") ? fxmlPath.substring(1) : fxmlPath;
                resource = Thread.currentThread().getContextClassLoader().getResource(path);
            }
            if (resource == null) {
                System.err.println("No se encontró el archivo FXML: " + fxmlPath);
                return false;
            }
            FXMLLoader loader = new FXMLLoader(resource);
            loader.setController(controller);
            Parent view = loader.load();
            if (view != null) {
                contentPane.getChildren().setAll(view);
                return true;
            } else {
                System.err.println("No se pudo cargar la vista con controlador: " + fxmlPath);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Error cargando la vista con controlador: " + fxmlPath);
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            System.err.println("No se encontró el archivo FXML: " + fxmlPath + 
                             ". Asegúrate de que el archivo existe en src/ui/");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene el StackPane del MainLayout desde cualquier controlador.
     * Busca recursivamente en la jerarquía de nodos.
     * 
     * @param node El nodo desde el cual buscar el StackPane
     * @return El StackPane encontrado o null si no se encuentra
     */
    public static StackPane findContentPane(javafx.scene.Node node) {
        if (node == null) {
            return null;
        }
        
        // Buscar en el padre
        javafx.scene.Node parent = node.getParent();
        while (parent != null) {
            if (parent instanceof StackPane) {
                StackPane stackPane = (StackPane) parent;
                // Verificar si tiene el id "contentPane" o es el contenedor principal
                if (stackPane.getId() != null && stackPane.getId().equals("contentPane")) {
                    return stackPane;
                }
                // Si es un StackPane y no tiene hijos o tiene solo uno, podría ser el contenedor
                if (stackPane.getChildren().size() <= 1) {
                    return stackPane;
                }
            }
            parent = parent.getParent();
        }
        
        return null;
    }
}
