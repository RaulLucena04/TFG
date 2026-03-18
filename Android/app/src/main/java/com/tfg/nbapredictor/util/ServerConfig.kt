package com.tfg.nbapredictor.util

import android.content.Context
import android.content.SharedPreferences

object ServerConfig {
    private const val PREFS_NAME = "server_config"
    private const val KEY_SERVER_URL = "server_url"
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
     * Asegura que la URL no termine con /
     */
    fun setServerUrl(context: Context, url: String) {
        val prefs = getSharedPreferences(context)
        val cleanUrl = url.trim().let { if (it.endsWith("/")) it.dropLast(1) else it }
        prefs.edit().putString(KEY_SERVER_URL, cleanUrl).apply()
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
     * Usa el contexto de la aplicación si está disponible.
     */
    fun setServerUrl(url: String) {
        val context = AppContext.get() ?: return
        setServerUrl(context, url)
    }
}
