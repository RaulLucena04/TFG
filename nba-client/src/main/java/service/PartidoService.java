package service;

import model.Partido;
import java.util.Arrays;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con partidos.
 * 
 * <p>Proporciona métodos para listar, crear y finalizar partidos mediante
 * comunicación con la API REST del servidor.
 * 
 * @author TFG
 * @version 1.0
 */
public class PartidoService {
    private final SocketApiClient api = new SocketApiClient();

    /**
     * Constructor del servicio de partidos.
     * 
     * <p>Inicializa el ObjectMapper con soporte para LocalDateTime.
     */
    public PartidoService() {
    }

    /**
     * Obtiene todos los partidos del sistema.
     * 
     * @return lista de todos los partidos, lista vacía si hay un error
     */
    public List<Partido> listarPartidos() {

        try {
            return api.request("match.list", java.util.Map.of(),
                    new com.fasterxml.jackson.core.type.TypeReference<List<Partido>>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }

    /**
     * Crea un nuevo partido en el sistema.
     * 
     * <p>Principalmente usado desde el panel de administración.
     * 
     * @param partido el partido a crear
     * @return el partido creado, o null si hay un error
     */
    public Partido crearPartido(Partido partido) {
        try {
            return api.request("match.create", partido, Partido.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finaliza un partido estableciendo el resultado.
     * 
     * <p>Al finalizar un partido, el servidor resuelve automáticamente
     * todas las apuestas relacionadas con ese partido.
     * 
     * @param id el ID del partido a finalizar
     * @param puntosLocal los puntos del equipo local
     * @param puntosVisitante los puntos del equipo visitante
     * @return true si la finalización fue exitosa, false en caso contrario
     */
    public boolean finalizarPartido(Long id, Integer puntosLocal, Integer puntosVisitante) {
        try {
            api.request("match.finalize",
                    java.util.Map.of("id", id, "puntosLocal", puntosLocal, "puntosVisitante", puntosVisitante),
                    Partido.class);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}