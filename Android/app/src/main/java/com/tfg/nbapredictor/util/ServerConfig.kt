package com.tfg.nbapredictor.util

import android.content.Context
import android.content.SharedPreferences

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
object ServerConfig {
    private const val PREFS_NAME = "server_config"
    private const val KEY_SERVER_URL = "server_url"
    /**
     * URL por defecto del servidor (para emulador Android).
     * 10.0.2.2 es la IP especial del emulador que apunta a localhost del host.
     */
    private const val DEFAULT_SERVER_URL = "http://10.0.2.2:8080"
    
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    /**
     * Obtiene la URL base del servidor configurada.
     * Si no hay configuración, devuelve la URL por defecto.
     */
    fun getServerUrl(context: Context): String {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_SERVER_URL, DEFAULT_SERVER_URL) ?: DEFAULT_SERVER_URL
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
    fun setServerUrl(context: Context, url: String) {
        val prefs = getSharedPreferences(context)
        val cleanUrl = url.trim().let { if (it.endsWith("/")) it.dropLast(1) else it }
        prefs.edit().putString(KEY_SERVER_URL, cleanUrl).apply()
        // Resetear el cliente Retrofit para que use la nueva URL
        try {
            com.tfg.nbapredictor.network.RetrofitClient.reset()
        } catch (e: Exception) {
            // Ignorar si RetrofitClient aún no está inicializado
        }
    }
    
    /**
     * Obtiene la URL base del servidor sin necesidad de Context.
     * Usa el contexto de la aplicación si está disponible.
     */
    fun getServerUrl(): String {
        val context = AppContext.get() ?: return DEFAULT_SERVER_URL
        return getServerUrl(context)
    }
    
    /**
     * Establece la URL base del servidor sin necesidad de Context.
     * 
     * <p>Usa el contexto de la aplicación almacenado en AppContext si está disponible.
     * Resetea automáticamente el cliente Retrofit para que use la nueva URL.
     * 
     * @param url la nueva URL del servidor (se normaliza automáticamente)
     */
    fun setServerUrl(url: String) {
        val context = AppContext.get() ?: return
        setServerUrl(context, url)
    }
}
