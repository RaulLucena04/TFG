package com.tfg.nbapredictor;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u00a8\u0006\u0005"}, d2 = {"Lcom/tfg/nbapredictor/NBAPredictorApplication;", "Landroid/app/Application;", "()V", "onCreate", "", "app_release"})
public final class NBAPredictorApplication extends android.app.Application {
    
    public NBAPredictorApplication() {
        super();
    }
    
    /**
     * Inicializa la aplicación cuando se crea.
     *
     * Establece el contexto de la aplicación en AppContext para acceso global.
     */
    @java.lang.Override()
    public void onCreate() {
    }
}