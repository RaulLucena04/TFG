package session;

import model.User;

/**
 * Clase que gestiona la sesión del usuario actual en la aplicación.
 * 
 * <p>Mantiene una referencia estática al usuario autenticado durante
 * toda la ejecución de la aplicación. Proporciona métodos para establecer,
 * obtener y limpiar la sesión del usuario.
 * 
 * @author TFG
 * @version 1.0
 */
public class Session {

    /**
     * Usuario actualmente autenticado en la aplicación.
     */
    private static User currentUser;

    /**
     * Establece el usuario actual de la sesión.
     * 
     * @param user el usuario a establecer como usuario actual
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Obtiene el usuario actual de la sesión.
     * 
     * @return el usuario actual, o null si no hay usuario autenticado
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Limpia la sesión actual, estableciendo el usuario a null.
     * 
     * <p>Se utiliza cuando el usuario cierra sesión.
     */
    public static void clear() {
        currentUser = null;
    }
}
