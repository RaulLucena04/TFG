package com.tfg.nbapredictor

import android.app.Application
import com.tfg.nbapredictor.util.AppContext

/**
 * Clase Application personalizada para la aplicación NBA Predictor.
 * 
 * <p>Inicializa el contexto de la aplicación para que esté disponible
 * globalmente a través de [AppContext]. Esto permite acceder al contexto
 * desde objetos singleton sin necesidad de pasar el contexto como parámetro.
 * 
 * <p>Esta clase debe estar registrada en el AndroidManifest.xml con el atributo
 * android:name en la etiqueta &lt;application&gt;.
 * 
 * @author TFG
 * @version 1.0
 */
class NBAPredictorApplication : Application() {
    /**
     * Inicializa la aplicación cuando se crea.
     * 
     * Establece el contexto de la aplicación en AppContext para acceso global.
     */
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
    }
}
