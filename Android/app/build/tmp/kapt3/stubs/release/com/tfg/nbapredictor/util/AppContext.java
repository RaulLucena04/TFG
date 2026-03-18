package com.tfg.nbapredictor.util;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/tfg/nbapredictor/util/AppContext;", "", "()V", "application", "Landroid/app/Application;", "get", "Landroid/content/Context;", "init", "", "app_release"})
public final class AppContext {
    @org.jetbrains.annotations.Nullable()
    private static android.app.Application application;
    @org.jetbrains.annotations.NotNull()
    public static final com.tfg.nbapredictor.util.AppContext INSTANCE = null;
    
    private AppContext() {
        super();
    }
    
    /**
     * Inicializa el contexto de la aplicación.
     *
     * Debe llamarse desde [Application.onCreate] para establecer la referencia.
     *
     * @param application la instancia de la aplicación
     */
    public final void init(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
    }
    
    /**
     * Obtiene el contexto de la aplicación.
     *
     * @return el contexto de la aplicación, o null si no se ha inicializado
     */
    @org.jetbrains.annotations.Nullable()
    public final android.content.Context get() {
        return null;
    }
}