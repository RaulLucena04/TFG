package com.tfg.nbapredictor.network;

/**
 * Cliente Retrofit singleton para realizar peticiones HTTP a la API REST del servidor.
 *
 * <p>Este objeto gestiona la creación y configuración del cliente Retrofit, incluyendo:
 * <ul>
 *  <li>Configuración de la URL base del servidor (obtenida de ServerConfig)</li>
 *  <li>Interceptores de logging para depuración</li>
 *  <li>Timeouts de conexión, lectura y escritura</li>
 *  <li>Adaptador Gson para serialización/deserialización JSON</li>
 *  <li>Adaptador personalizado para LocalDateTime</li>
 * </ul>
 *
 * <p>El cliente se recrea automáticamente cuando cambia la URL del servidor,
 * permitiendo cambiar la configuración sin reiniciar la aplicación.
 *
 * @author TFG
 * @version 1.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\tH\u0002J\b\u0010\u0013\u001a\u00020\u000bH\u0002J\u0006\u0010\u0014\u001a\u00020\u0015R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\r8\u0002X\u0083\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/tfg/nbapredictor/network/RetrofitClient;", "", "()V", "apiService", "Lcom/tfg/nbapredictor/network/ApiService;", "getApiService", "()Lcom/tfg/nbapredictor/network/ApiService;", "cachedApiService", "cachedBaseUrl", "", "cachedRetrofit", "Lretrofit2/Retrofit;", "gson", "Lcom/google/gson/Gson;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "okHttpClient", "Lokhttp3/OkHttpClient;", "getBaseUrl", "getRetrofit", "reset", "", "app_debug"})
@androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
public final class RetrofitClient {
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.OkHttpClient okHttpClient = null;
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @org.jetbrains.annotations.NotNull()
    private static final com.google.gson.Gson gson = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile java.lang.String cachedBaseUrl;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile retrofit2.Retrofit cachedRetrofit;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.tfg.nbapredictor.network.ApiService cachedApiService;
    @org.jetbrains.annotations.NotNull()
    public static final com.tfg.nbapredictor.network.RetrofitClient INSTANCE = null;
    
    private RetrofitClient() {
        super();
    }
    
    private final java.lang.String getBaseUrl() {
        return null;
    }
    
    private final retrofit2.Retrofit getRetrofit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.tfg.nbapredictor.network.ApiService getApiService() {
        return null;
    }
    
    /**
     * Fuerza la recreación del cliente Retrofit limpiando todas las instancias cacheadas.
     *
     * <p>Útil cuando se cambia la configuración del servidor mediante ServerConfig.
     * La próxima vez que se acceda a apiService, se creará una nueva instancia
     * con la URL actualizada.
     *
     * <p>Este método es llamado automáticamente por ServerConfig.setServerUrl()
     * cuando se actualiza la URL del servidor.
     */
    public final void reset() {
    }
}