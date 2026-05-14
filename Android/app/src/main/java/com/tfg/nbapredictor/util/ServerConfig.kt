package com.tfg.nbapredictor.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import com.tfg.nbapredictor.network.RetrofitClient

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
object ServerConfig {
    private const val PREFS_NAME = "server_config"
    private const val KEY_SERVER_HOST = "server_host"
    private const val KEY_SERVER_PORT = "server_port"
    /**
     * Host/puerto por defecto del servidor (para emulador Android).
     * 10.0.2.2 es la IP especial del emulador que apunta a localhost del host.
     */
    private const val DEFAULT_SERVER_HOST = "10.0.2.2"
    private const val DEFAULT_SERVER_PORT = 9090

    private const val TAG = "ServerConfig"

    /**
     * Heurística para saber si la app corre en emulador. En emulador, [DEFAULT_SERVER_HOST] apunta al PC anfitrión.
     * En dispositivo físico hay que configurar la IP LAN del PC (misma Wi‑Fi) en el diálogo de login.
     */
    fun isProbablyEmulator(): Boolean {
        return Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk", ignoreCase = true)
            || Build.MODEL.contains("Emulator", ignoreCase = true)
            || Build.MODEL.contains("Android SDK built for x86", ignoreCase = true)
            || Build.MANUFACTURER.contains("Genymotion", ignoreCase = true)
            || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
            || "google_sdk" == Build.PRODUCT
    }
    
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    /**
     * Obtiene la URL base del servidor configurada.
     * Si no hay configuración, devuelve la URL por defecto.
     */
    fun getServerHost(context: Context): String {
        val prefs = getSharedPreferences(context)
        // Migración legacy: si existe server_url (HTTP), extraer host/puerto.
        val legacyUrl = prefs.getString("server_url", null)
        if (!legacyUrl.isNullOrBlank()) {
            val (h, p) = parseHostPort(legacyUrl)
            setServerAddress(context, h, p)
            return h
        }
        return prefs.getString(KEY_SERVER_HOST, DEFAULT_SERVER_HOST) ?: DEFAULT_SERVER_HOST
    }

    fun getServerPort(context: Context): Int {
        val prefs = getSharedPreferences(context)
        val legacyUrl = prefs.getString("server_url", null)
        if (!legacyUrl.isNullOrBlank()) {
            val (_, p) = parseHostPort(legacyUrl)
            setServerAddress(context, getServerHost(context), p)
            return p
        }
        return prefs.getInt(KEY_SERVER_PORT, DEFAULT_SERVER_PORT)
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
    fun setServerAddress(context: Context, host: String, port: Int) {
        val prefs = getSharedPreferences(context)
        val cleanHost = host.trim().ifBlank { DEFAULT_SERVER_HOST }
        val cleanPort = port.coerceIn(1, 65535)
        prefs.edit()
            .putString(KEY_SERVER_HOST, cleanHost)
            .putInt(KEY_SERVER_PORT, cleanPort)
            .apply()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                RetrofitClient.reset()
            } catch (_: Throwable) {
                // Retrofit puede no estar disponible en tests
            }
        }
    }

    /**
     * Parsea "host:puerto" o "http://host:puerto" y guarda en preferencias.
     */
    fun applyFromHostPortString(context: Context, hostPort: String) {
        val (h, p) = parseHostPort(hostPort)
        setServerAddress(context, h, p)
    }

    /**
     * Obtiene la URL base del servidor sin necesidad de Context.
     * Usa el contexto de la aplicación si está disponible.
     */
    fun getServerHost(): String {
        val context = AppContext.get() ?: return DEFAULT_SERVER_HOST
        return getServerHost(context)
    }

    fun getServerPort(): Int {
        val context = AppContext.get() ?: return DEFAULT_SERVER_PORT
        return getServerPort(context)
    }
    
    /**
     * Establece la URL base del servidor sin necesidad de Context.
     * 
     * <p>Usa el contexto de la aplicación almacenado en AppContext si está disponible.
     * Resetea automáticamente el cliente Retrofit para que use la nueva URL.
     * 
     * @param url la nueva URL del servidor (se normaliza automáticamente)
     */
    fun setServerAddress(hostPort: String) {
        val context = AppContext.get()
        if (context == null) {
            Log.e(TAG, "AppContext no inicializado: no se puede guardar servidor ($hostPort). Usa applyFromHostPortString(context, ...) desde UI.")
            return
        }
        applyFromHostPortString(context, hostPort)
    }

    private fun parseHostPort(value: String): Pair<String, Int> {
        val trimmed = value.trim()
        return try {
            // Permite http://host:port, tcp://host:port o host:port
            val uri = if (trimmed.contains("://")) {
                android.net.Uri.parse(trimmed)
            } else {
                android.net.Uri.parse("tcp://$trimmed")
            }
            val host = uri.host ?: DEFAULT_SERVER_HOST
            val port = if (uri.port != -1) uri.port else DEFAULT_SERVER_PORT
            host to port
        } catch (_: Exception) {
            DEFAULT_SERVER_HOST to DEFAULT_SERVER_PORT
        }
    }
}
