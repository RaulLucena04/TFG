package com.tfg.nbapredictor.util;

/**
 * Objeto singleton que gestiona la sesión del usuario actual en la aplicación Android.
 *
 * Mantiene una referencia al usuario autenticado y proporciona un flujo de eventos
 * para notificar cuando el usuario se actualiza, permitiendo que las pantallas
 * actualicen automáticamente los datos mostrados (como los puntos).
 *
 * @author TFG
 * @version 1.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\f\u001a\u00020\u0005J\b\u0010\r\u001a\u0004\u0018\u00010\u0007J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0014"}, d2 = {"Lcom/tfg/nbapredictor/util/Session;", "", "()V", "_userUpdated", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "currentUser", "Lcom/tfg/nbapredictor/model/User;", "userUpdated", "Lkotlinx/coroutines/flow/SharedFlow;", "getUserUpdated", "()Lkotlinx/coroutines/flow/SharedFlow;", "clearSession", "getCurrentUser", "isAdmin", "", "isLoggedIn", "notifyUserUpdated", "setCurrentUser", "user", "app_release"})
public final class Session {
    @org.jetbrains.annotations.Nullable()
    private static com.tfg.nbapredictor.model.User currentUser;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Unit> _userUpdated = null;
    
    /**
     * Flujo de eventos que se emite cuando el usuario se actualiza.
     *
     * Las pantallas pueden escuchar este flujo para actualizar automáticamente
     * los datos cuando cambian los puntos del usuario.
     */
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> userUpdated = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tfg.nbapredictor.util.Session INSTANCE = null;
    
    private Session() {
        super();
    }
    
    /**
     * Flujo de eventos que se emite cuando el usuario se actualiza.
     *
     * Las pantallas pueden escuchar este flujo para actualizar automáticamente
     * los datos cuando cambian los puntos del usuario.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> getUserUpdated() {
        return null;
    }
    
    /**
     * Establece el usuario actual de la sesión.
     *
     * @param user el usuario a establecer como usuario actual
     */
    public final void setCurrentUser(@org.jetbrains.annotations.NotNull()
    com.tfg.nbapredictor.model.User user) {
    }
    
    /**
     * Notifica que el usuario ha sido actualizado.
     *
     * Emite un evento en el flujo [userUpdated] para que las pantallas
     * que escuchan este flujo puedan actualizar sus datos automáticamente.
     *
     * Debe llamarse después de actualizar el usuario con datos del servidor
     * (por ejemplo, después de crear una apuesta o canjear puntos).
     */
    public final void notifyUserUpdated() {
    }
    
    /**
     * Obtiene el usuario actual de la sesión.
     *
     * @return el usuario actual, o null si no hay usuario autenticado
     */
    @org.jetbrains.annotations.Nullable()
    public final com.tfg.nbapredictor.model.User getCurrentUser() {
        return null;
    }
    
    /**
     * Limpia la sesión actual, estableciendo el usuario a null.
     *
     * Se utiliza cuando el usuario cierra sesión.
     */
    public final void clearSession() {
    }
    
    /**
     * Verifica si hay un usuario autenticado en la sesión.
     *
     * @return true si hay un usuario autenticado, false en caso contrario
     */
    public final boolean isLoggedIn() {
        return false;
    }
    
    /**
     * Verifica si el usuario actual es administrador.
     *
     * @return true si el usuario actual es administrador, false en caso contrario
     */
    public final boolean isAdmin() {
        return false;
    }
}