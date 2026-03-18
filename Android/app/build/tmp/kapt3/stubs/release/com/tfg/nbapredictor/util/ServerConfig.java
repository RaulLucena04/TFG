package com.tfg.nbapredictor.util;

/**
 * Objeto singleton que gestiona la configuración de la URL del servidor.
 *
 * Almacena la URL del servidor en SharedPreferences y proporciona métodos
 * para obtener y establecer la configuración. Cuando se cambia la URL,
 * automáticamente resetea el cliente Retrofit para usar la nueva configuración.
 *
 * @author TFG
 * @version 1.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/tfg/nbapredictor/util/ServerConfig;", "", "()V", "DEFAULT_SERVER_URL", "", "KEY_SERVER_URL", "PREFS_NAME", "getServerUrl", "context", "Landroid/content/Context;", "getSharedPreferences", "Landroid/content/SharedPreferences;", "setServerUrl", "", "url", "app_release"})
public final class ServerConfig {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "server_config";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SERVER_URL = "server_url";
    
    /**
     * URL por defecto del servidor (para emulador Android).
     * 10.0.2.2 es la IP especial del emulador que apunta a localhost del host.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DEFAULT_SERVER_URL = "http://10.0.2.2:8080";
    @org.jetbrains.annotations.NotNull()
    public static final com.tfg.nbapredictor.util.ServerConfig INSTANCE = null;
    
    private ServerConfig() {
        super();
    }
    
    private final android.content.SharedPreferences getSharedPreferences(android.content.Context context) {
        return null;
    }
    
    /**
     * Obtiene la URL base del servidor configurada.
     * Si no hay configuración, devuelve la URL por defecto.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getServerUrl(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
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
    public final void setServerUrl(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    /**
     * Obtiene la URL base del servidor sin necesidad de Context.
     * Usa el contexto de la aplicación si está disponible.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getServerUrl() {
        return null;
    }
    
    /**
     * Establece la URL base del servidor sin necesidad de Context.
     *
     * <p>Usa el contexto de la aplicación almacenado en AppContext si está disponible.
     * Resetea automáticamente el cliente Retrofit para que use la nueva URL.
     *
     * @param url la nueva URL del servidor (se normaliza automáticamente)
     */
    public final void setServerUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
}