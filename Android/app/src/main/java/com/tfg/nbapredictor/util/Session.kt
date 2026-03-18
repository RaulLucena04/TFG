package com.tfg.nbapredictor.util

import com.tfg.nbapredictor.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

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
object Session {
    private var currentUser: User? = null

    private val _userUpdated = MutableSharedFlow<Unit>(replay = 0)
    
    /**
     * Flujo de eventos que se emite cuando el usuario se actualiza.
     * 
     * Las pantallas pueden escuchar este flujo para actualizar automáticamente
     * los datos cuando cambian los puntos del usuario.
     */
    val userUpdated: SharedFlow<Unit> = _userUpdated

    /**
     * Establece el usuario actual de la sesión.
     * 
     * @param user el usuario a establecer como usuario actual
     */
    fun setCurrentUser(user: User) {
        currentUser = user
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
    fun notifyUserUpdated() {
        _userUpdated.tryEmit(Unit)
    }
    
    /**
     * Obtiene el usuario actual de la sesión.
     * 
     * @return el usuario actual, o null si no hay usuario autenticado
     */
    fun getCurrentUser(): User? = currentUser
    
    /**
     * Limpia la sesión actual, estableciendo el usuario a null.
     * 
     * Se utiliza cuando el usuario cierra sesión.
     */
    fun clearSession() {
        currentUser = null
    }
    
    /**
     * Verifica si hay un usuario autenticado en la sesión.
     * 
     * @return true si hay un usuario autenticado, false en caso contrario
     */
    fun isLoggedIn(): Boolean = currentUser != null
    
    /**
     * Verifica si el usuario actual es administrador.
     * 
     * @return true si el usuario actual es administrador, false en caso contrario
     */
    fun isAdmin(): Boolean = currentUser?.isAdmin() ?: false
}
