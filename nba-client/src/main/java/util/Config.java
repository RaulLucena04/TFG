package util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase de configuración para gestionar la URL base del servidor.
 * 
 * <p>Lee y guarda la configuración en un archivo config.properties en el directorio
 * de ejecución. Normaliza las URLs eliminando barras finales para mantener consistencia.
 * 
 * <p>Proporciona un diálogo JavaFX para configurar la IP del servidor al inicio
 * de la aplicación, permitiendo conectar a servidores en diferentes máquinas.
 * 
 * @author TFG
 * @version 1.0
 */
public class Config {
    private static final String CONFIG_FILE = "config.properties";
    private static final String DEFAULT_SERVER_URL = "http://localhost:8080";
    private static String serverUrl = DEFAULT_SERVER_URL;
    
    static {
        loadConfig();
    }
    
    /**
     * Carga la configuración desde el archivo config.properties.
     * 
     * <p>Si el archivo no existe, crea uno con valores por defecto.
     * Normaliza la URL eliminando cualquier barra final para mantener consistencia.
     */
    private static void loadConfig() {
        Properties props = new Properties();
        File configFile = new File(CONFIG_FILE);
        
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                props.load(reader);
                String url = props.getProperty("server.url", DEFAULT_SERVER_URL);
                // Asegurar que la URL no termine con /
                serverUrl = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
            } catch (IOException e) {
                System.err.println("Error al cargar configuración: " + e.getMessage());
                serverUrl = DEFAULT_SERVER_URL;
            }
        } else {
            // Crear archivo de configuración por defecto
            saveConfig();
        }
    }
    
    /**
     * Guarda la configuración actual en el archivo config.properties.
     */
    private static void saveConfig() {
        Properties props = new Properties();
        props.setProperty("server.url", serverUrl);
        
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            props.store(writer, "Configuración del servidor");
        } catch (IOException e) {
            System.err.println("Error al guardar configuración: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la URL base del servidor.
     * @return La URL base del servidor (sin barra final)
     */
    public static String getServerUrl() {
        return serverUrl;
    }
    
    /**
     * Establece la URL base del servidor y guarda la configuración.
     * 
     * <p>Normaliza la URL eliminando espacios en blanco y barras finales
     * para mantener consistencia. La configuración se guarda automáticamente
     * en el archivo config.properties.
     * 
     * @param url La nueva URL del servidor (se normaliza automáticamente)
     */
    public static void setServerUrl(String url) {
        if (url != null && !url.trim().isEmpty()) {
            // Asegurar que la URL no termine con /
            serverUrl = url.trim().endsWith("/") ? url.trim().substring(0, url.trim().length() - 1) : url.trim();
            saveConfig();
        }
    }
    
    /**
     * Muestra un diálogo JavaFX para configurar la IP del servidor al inicio de la aplicación.
     * 
     * <p>Permite al usuario introducir la URL del servidor (por ejemplo, http://192.168.1.100:8080)
     * para conectar a un servidor en una máquina diferente. Si el usuario cancela,
     * se mantiene la configuración actual o la URL por defecto.
     * 
     * <p>Debe llamarse desde el hilo de JavaFX (se ejecuta con Platform.runLater).
     */
    public static void promptServerUrl() {
        // Esperar a que JavaFX esté listo
        javafx.application.Platform.runLater(() -> {
            try {
                javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog(serverUrl);
                dialog.setTitle("Configuración del Servidor");
                dialog.setHeaderText("Configurar IP del Servidor");
                dialog.setContentText("Introduce la URL del servidor (ej: http://192.168.1.100:8080):");
                
                java.util.Optional<String> result = dialog.showAndWait();
                result.ifPresent(url -> {
                    if (!url.trim().isEmpty()) {
                        setServerUrl(url);
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                        alert.setTitle("Configuración Guardada");
                        alert.setHeaderText(null);
                        alert.setContentText("URL del servidor configurada: " + serverUrl);
                        alert.showAndWait();
                    }
                });
            } catch (Exception e) {
                System.err.println("Error al mostrar diálogo de configuración: " + e.getMessage());
            }
        });
    }
}
