package com.tfg.nbapredictor.util;

/**
 * Objeto singleton que gestiona la configuración de la URL del servidor.
 *
 * Almacena la URL del servidor en SharedPreferences y proporciona métodos
 * para obtener y establecer la configuración. Cuando se cambia la URL,
 * automáticamente resetea el cliente Retrofit para usar la nueva configuración.
 *
 * <p><b>Importante:</b> la app Android <b>no</b> se conecta directamente a MySQL. Habla con el
 * backend Java por <b>TCP (socket)</b> en [host]:9090; el backend es quien usa la base de datos.</p>
 *
 * @author TFG
 * @version 1.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0004J\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u0011\u001a\u00020\u0006J\u000e\u0010\u0011\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0006\u0010\u0014\u001a\u00020\u0015J\u001c\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\u00172\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u001e\u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0006J\u000e\u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/tfg/nbapredictor/util/ServerConfig;", "", "()V", "DEFAULT_SERVER_HOST", "", "DEFAULT_SERVER_PORT", "", "KEY_SERVER_HOST", "KEY_SERVER_PORT", "PREFS_NAME", "TAG", "applyFromHostPortString", "", "context", "Landroid/content/Context;", "hostPort", "getServerHost", "getServerPort", "getSharedPreferences", "Landroid/content/SharedPreferences;", "isProbablyEmulator", "", "parseHostPort", "Lkotlin/Pair;", "value", "setServerAddress", "host", "port", "app_debug"})
public final class ServerConfig {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "server_config";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SERVER_HOST = "server_host";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SERVER_PORT = "server_port";
    
    /**
     * Host/puerto por defecto del servidor (para emulador Android).
     * 10.0.2.2 es la IP especial del emulador que apunta a localhost del host.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DEFAULT_SERVER_HOST = "10.0.2.2";
    private static final int DEFAULT_SERVER_PORT = 9090;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ServerConfig";
    @org.jetbrains.annotations.NotNull()
    public static final com.tfg.nbapredictor.util.ServerConfig INSTANCE = null;
    
    private ServerConfig() {
        super();
    }
    
    /**
     * Heurística para saber si la app corre en emulador. En emulador, [DEFAULT_SERVER_HOST] apunta al PC anfitrión.
     * En dispositivo físico hay que configurar la IP LAN del PC (misma Wi‑Fi) en el diálogo de login.
     */
    public final boolean isProbablyEmulator() {
        return false;
    }
    
    private final android.content.SharedPreferences getSharedPreferences(android.content.Context context) {
        return null;
    }
    
    /**
     * Obtiene la URL base del servidor configurada.
     * Si no hay configuración, devuelve la URL por defecto.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getServerHost(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final int getServerPort(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    /**
     * Establece la URL base del servidor.
     *
     * <p>Normaliza la URL eliminando espacios en blanco y barras finales.
     * Resetea automáticamente el cliente Retrofit para que use la nueva URL
     * sin necesidad de reiniciar la aplicación. Esto permite cambiar la configuración
     * del servidor dinámicamente.
     *
     * @param context el contexto de la aplicación
     * @param url la nueva URL del servidor (se normaliza automáticamente)
     */
    public final void setServerAddress(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String host, int port) {
    }
    
    /**
     * Parsea "host:puerto" o "http://host:puerto" y guarda en preferencias.
     */
    public final void applyFromHostPortString(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String hostPort) {
    }
    
    /**
     * Obtiene la URL base del servidor sin necesidad de Context.
     * Usa el contexto de la aplicación si está disponible.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getServerHost() {
        return null;
    }
    
    public final int getServerPort() {
        return 0;
    }
    
    /**
     * Establece la URL base del servidor sin necesidad de Context.
     *
     * <p>Usa el contexto de la aplicación almacenado en AppContext si está disponible.
     * Resetea automáticamente el cliente Retrofit para que use la nueva URL.
     *
     * @param url la nueva URL del servidor (se normaliza automáticamente)
     */
    public final void setServerAddress(@org.jetbrains.annotations.NotNull()
    java.lang.String hostPort) {
    }
    
    private final kotlin.Pair<java.lang.String, java.lang.Integer> parseHostPort(java.lang.String value) {
        return null;
    }
}