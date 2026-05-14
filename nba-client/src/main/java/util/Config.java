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
    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int DEFAULT_SERVER_PORT = 9090;
    private static String serverHost = DEFAULT_SERVER_HOST;
    private static int serverPort = DEFAULT_SERVER_PORT;
    
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
                String host = props.getProperty("server.host");
                String portStr = props.getProperty("server.port");

                if (host != null && !host.isBlank() && portStr != null && !portStr.isBlank()) {
                    serverHost = host.trim();
                    serverPort = Integer.parseInt(portStr.trim());
                    return;
                }

                // Migración: si existe la antigua server.url (HTTP), extraer host/puerto.
                String legacyUrl = props.getProperty("server.url");
                if (legacyUrl != null && !legacyUrl.isBlank()) {
                    HostPort hp = parseHostPort(legacyUrl.trim());
                    serverHost = hp.host;
                    serverPort = hp.port;
                    saveConfig();
                    return;
                }

                serverHost = DEFAULT_SERVER_HOST;
                serverPort = DEFAULT_SERVER_PORT;
            } catch (IOException e) {
                System.err.println("Error al cargar configuración: " + e.getMessage());
                serverHost = DEFAULT_SERVER_HOST;
                serverPort = DEFAULT_SERVER_PORT;
            } catch (Exception e) {
                System.err.println("Configuración inválida, usando valores por defecto: " + e.getMessage());
                serverHost = DEFAULT_SERVER_HOST;
                serverPort = DEFAULT_SERVER_PORT;
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
        props.setProperty("server.host", serverHost);
        props.setProperty("server.port", String.valueOf(serverPort));
        
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            props.store(writer, "Configuración del servidor");
        } catch (IOException e) {
            System.err.println("Error al guardar configuración: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene el host del servidor de sockets.
     */
    public static String getServerHost() {
        return serverHost;
    }

    /**
     * Obtiene el puerto del servidor de sockets.
     */
    public static int getServerPort() {
        return serverPort;
    }
    
    /**
     * Establece host y puerto del servidor y guarda la configuración.
     * 
     * @param host host o IP
     * @param port puerto TCP
     */
    public static void setServerAddress(String host, int port) {
        if (host == null || host.trim().isEmpty()) return;
        if (port <= 0 || port > 65535) return;
        serverHost = host.trim();
        serverPort = port;
        saveConfig();
    }
    
    /**
     * Muestra un diálogo JavaFX para configurar la IP del servidor al inicio de la aplicación.
     * 
     * <p>Permite al usuario introducir host:puerto (por ejemplo, 192.168.1.100:9090)
     * para conectar a un servidor en una máquina diferente.
     * 
     * <p>Debe llamarse desde el hilo de JavaFX (se ejecuta con Platform.runLater).
     */
    public static void promptServerUrl() {
        // Esperar a que JavaFX esté listo
        javafx.application.Platform.runLater(() -> {
            try {
                javafx.scene.control.TextInputDialog dialog =
                        new javafx.scene.control.TextInputDialog(serverHost + ":" + serverPort);
                dialog.setTitle("Configuración del Servidor");
                dialog.setHeaderText("Configurar IP del Servidor");
                dialog.setContentText("Introduce host:puerto (ej: 192.168.1.100:9090):");
                
                java.util.Optional<String> result = dialog.showAndWait();
                result.ifPresent(value -> {
                    if (!value.trim().isEmpty()) {
                        HostPort hp = parseHostPort(value.trim());
                        setServerAddress(hp.host, hp.port);
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                        alert.setTitle("Configuración Guardada");
                        alert.setHeaderText(null);
                        alert.setContentText("Servidor configurado: " + serverHost + ":" + serverPort);
                        alert.showAndWait();
                    }
                });
            } catch (Exception e) {
                System.err.println("Error al mostrar diálogo de configuración: " + e.getMessage());
            }
        });
    }

    private record HostPort(String host, int port) {}

    private static HostPort parseHostPort(String value) {
        // Permitir valores legacy tipo http://host:puerto o host:puerto
        String trimmed = value.trim();
        try {
            java.net.URI uri;
            if (trimmed.contains("://")) {
                uri = java.net.URI.create(trimmed);
            } else {
                // Si viene "host:puerto", forzamos esquema para que URI lo parse correctamente
                uri = java.net.URI.create("tcp://" + trimmed);
            }
            String host = uri.getHost();
            int port = uri.getPort();
            if (host == null || host.isBlank()) {
                // Caso raro: "localhost" sin puerto
                host = trimmed.contains(":") ? trimmed.substring(0, trimmed.indexOf(':')) : trimmed;
            }
            if (port <= 0) port = DEFAULT_SERVER_PORT;
            return new HostPort(host, port);
        } catch (Exception e) {
            return new HostPort(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
        }
    }
}
