package service;

import model.Jugador;
import java.util.List;

/**
 * Servicio cliente para operaciones relacionadas con jugadores.
 *
 * <p>Encapsula llamadas al backend por sockets para listar jugadores.</p>
 */
public class JugadorService {
    private final SocketApiClient api = new SocketApiClient();

    public List<Jugador> getAllPlayers() throws Exception {
        return api.request("player.list", java.util.Map.of(),
                new com.fasterxml.jackson.core.type.TypeReference<List<Jugador>>() {});
    }
}