package com.tfg.nbapredictor.util

import android.app.Application
import android.content.Context

/**
 * Objeto singleton que mantiene una referencia al contexto de la aplicación.
 * 
 * Permite acceder al contexto de la aplicación desde cualquier parte del código
 * sin necesidad de pasar el contexto como parámetro. Útil para clases que no
 * tienen acceso directo al contexto (como objetos singleton).
 * 
 * <p>Debe inicializarse en la clase Application con [init].
 * 
 * @author TFG
 * @version 1.0
 */
object AppContext {
    private var application: Application? = null
    
    /**
     * Inicializa el contexto de la aplicación.
     * 
     * Debe llamarse desde [Application.onCreate] para establecer la referencia.
     * 
     * @param application la instancia de la aplicación
     */
    fun init(application: Application) {
        this.application = application
    }
    
    /**
     * Obtiene el contexto de la aplicación.
     * 
     * @return el contexto de la aplicación, o null si no se ha inicializado
     */
    fun get(): Context? {
        return application?.applicationContext
    }
}
