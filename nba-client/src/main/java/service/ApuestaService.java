package service;

import java.util.List;

import model.Apuesta;

/**
 * Servicio que gestiona las operaciones relacionadas con apuestas.
 * 
 * <p>Proporciona métodos para crear apuestas y obtener apuestas de usuarios
 * mediante comunicación con la API REST del servidor.
 * 
 * @author TFG
 * @version 1.0
 */
public class ApuestaService {
    private final SocketApiClient api = new SocketApiClient();

    /**
     * Obtiene todas las apuestas realizadas por un usuario.
     * 
     * @param userId el ID del usuario
     * @return lista de apuestas del usuario, lista vacía si hay un error
     */
    public List<Apuesta> obtenerApuestasUsuario(Long userId) {

        try {
            return api.request("bet.byUser",
                    java.util.Map.of("userId", userId),
                    new com.fasterxml.jackson.core.type.TypeReference<List<Apuesta>>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }

    /**
     * Crea una nueva apuesta en el sistema.
     * 
     * <p>Envía la apuesta al servidor que valida que el usuario tenga suficientes
     * puntos, calcula la cuota si no está especificada y descuenta los puntos.
     * 
     * @param apuesta la apuesta a crear con usuario, partido, predicción y puntos apostados
     * @throws RuntimeException si el usuario no tiene suficientes puntos, el partido no existe
     *                          o hay un error de conexión
     */
    public void crearApuesta(Apuesta apuesta) throws RuntimeException {

        try {
            api.request("bet.create", apuesta, Apuesta.class);

        } catch (RuntimeException e) {
            // Re-lanzar RuntimeException para que el controlador pueda manejarla
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error de conexión al crear apuesta: " + e.getMessage());
        }
    }
}