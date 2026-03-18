package com.tfg.nbapredictor.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tfg.nbapredictor.util.ServerConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

/**
 * Cliente Retrofit singleton para realizar peticiones HTTP a la API REST del servidor.
 * 
 * <p>Este objeto gestiona la creación y configuración del cliente Retrofit, incluyendo:
 * <ul>
 *   <li>Configuración de la URL base del servidor (obtenida de ServerConfig)</li>
 *   <li>Interceptores de logging para depuración</li>
 *   <li>Timeouts de conexión, lectura y escritura</li>
 *   <li>Adaptador Gson para serialización/deserialización JSON</li>
 *   <li>Adaptador personalizado para LocalDateTime</li>
 * </ul>
 * 
 * <p>El cliente se recrea automáticamente cuando cambia la URL del servidor,
 * permitiendo cambiar la configuración sin reiniciar la aplicación.
 * 
 * @author TFG
 * @version 1.0
 */
@RequiresApi(Build.VERSION_CODES.O)
object RetrofitClient {

    private fun getBaseUrl(): String {
        val url = ServerConfig.getServerUrl()
        // Asegurar que termine con /
        return if (url.endsWith("/")) url else "$url/"
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @RequiresApi(Build.VERSION_CODES.O)
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    // Variable para cachear la URL actual y detectar cambios
    @Volatile
    private var cachedBaseUrl: String? = null
    
    @Volatile
    private var cachedRetrofit: Retrofit? = null
    
    @Volatile
    private var cachedApiService: ApiService? = null

    private fun getRetrofit(): Retrofit {
        val currentUrl = getBaseUrl()
        // Si la URL cambió o no hay retrofit cacheado, crear uno nuevo
        if (cachedRetrofit == null || cachedBaseUrl != currentUrl) {
            synchronized(this) {
                // Double-check locking
                if (cachedRetrofit == null || cachedBaseUrl != currentUrl) {
                    cachedBaseUrl = currentUrl
                    cachedRetrofit = Retrofit.Builder()
                        .baseUrl(currentUrl)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                    // Recrear también el apiService cuando cambia el retrofit
                    cachedApiService = cachedRetrofit!!.create(ApiService::class.java)
                }
            }
        }
        return cachedRetrofit!!
    }

    /**
     * Obtiene el servicio API, recreándolo automáticamente si la URL del servidor cambió.
     * 
     * <p>Verifica si la URL actual difiere de la URL cacheada y recrea el cliente
     * Retrofit y el servicio API si es necesario. Esto permite cambiar la configuración
     * del servidor sin reiniciar la aplicación.
     * 
     * @return la instancia del servicio API configurada con la URL actual
     */
    val apiService: ApiService
        get() {
            val currentUrl = getBaseUrl()
            if (cachedApiService == null || cachedBaseUrl != currentUrl) {
                getRetrofit() // Esto recreará el servicio si es necesario
            }
            return cachedApiService!!
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
    fun reset() {
        synchronized(this) {
            cachedBaseUrl = null
            cachedRetrofit = null
            cachedApiService = null
        }
    }
}
