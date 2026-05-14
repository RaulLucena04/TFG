package service;

import model.User;

/**
 * Servicio que gestiona las operaciones relacionadas con usuarios.
 * 
 * <p>Proporciona métodos para autenticación, actualización de contraseña,
 * consulta de usuarios y actualización de datos de usuario mediante
 * comunicación con la API REST del servidor.
 * 
 * @author TFG
 * @version 1.0
 */
public class UsuarioService {
    private final SocketApiClient api = new SocketApiClient();

    /**
     * Autentica un usuario con username y contraseña.
     * 
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @return el usuario autenticado si las credenciales son correctas, null en caso contrario
     */
    public User login(String username, String password) {
        try {
            return api.request("user.login",
                    java.util.Map.of("username", username, "password", password),
                    User.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Actualiza la contraseña de un usuario.
     * 
     * @param id el ID del usuario
     * @param newPassword la nueva contraseña
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean updatePassword(Long id, String newPassword) {
        try {
            api.request("user.changePassword",
                    java.util.Map.of("id", id, "password", newPassword),
                    new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Object>>() {});
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los usuarios del sistema ordenados por puntos descendente.
     * 
     * @return lista de usuarios ordenada por puntos (mayor a menor)
     */
    public java.util.List<User> listarUsuarios() {

        try {
            java.util.List<User> usuarios = api.request("user.list",
                    java.util.Map.of(),
                    new com.fasterxml.jackson.core.type.TypeReference<java.util.List<User>>() {});

            // El backend ya lo devuelve ordenado, pero lo reforzamos por seguridad.
            return usuarios.stream()
                    .sorted(java.util.Comparator.comparingInt(User::getPoints).reversed())
                    .toList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return java.util.List.of();
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param usuario el usuario con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarUsuario(User usuario) {
        try {
            api.request("user.update", usuario, User.class);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene un usuario por su identificador.
     * 
     * @param id el ID del usuario
     * @return el usuario encontrado, o null si no existe o hay un error
     */
    public User obtenerUsuarioPorId(Long id) {
        try {
            return api.request("user.get", java.util.Map.of("id", id), User.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
