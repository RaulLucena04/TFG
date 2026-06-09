package com.tfg.nbabackend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.nbabackend.enums.EstadoPartido;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.repository.PartidoRepository;

/**
 * Servicio que gestiona las operaciones relacionadas con partidos.
 * 
 * <p>Este servicio se encarga de listar, crear y finalizar partidos.
 * Cuando se finaliza un partido, se resuelven automáticamente todas las apuestas relacionadas.
 * 
 * @author TFG
 * @version 1.0
 */
@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final ApuestaService apuestaService;

    /**
     * Constructor del servicio de partidos.
     * 
     * @param partidoRepository repositorio de partidos
     * @param apuestaService servicio de apuestas para resolver apuestas al finalizar partidos
     */
    public PartidoService(PartidoRepository partidoRepository,
            ApuestaService apuestaService) {
        this.partidoRepository = partidoRepository;
        this.apuestaService = apuestaService;
    }

    /**
     * Obtiene todos los partidos del sistema.
     *
     * @return lista de todos los partidos
     */
    @Transactional(readOnly = true)
    public List<Partido> listarPartidos() {
        return partidoRepository.findAllWithEquipos();
    }

    /**
     * Guarda un nuevo partido o actualiza uno existente.
     * 
     * @param partido el partido a guardar
     * @return el partido guardado
     */
    public Partido guardar(Partido partido) {
        return partidoRepository.save(partido);
    }

    /**
     * Finaliza un partido estableciendo el resultado y resolviendo las apuestas.
     * 
     * <p>Establece los puntos de ambos equipos, cambia el estado a FINALIZADO
     * y resuelve automáticamente todas las apuestas relacionadas con el partido.
     * 
     * @param id el ID del partido a finalizar
     * @param puntosLocal los puntos del equipo local
     * @param puntosVisitante los puntos del equipo visitante
     * @return el partido finalizado
     * @throws RuntimeException si el partido no existe o ya está finalizado
     */
    @Transactional
    public Partido finalizarPartido(Long id, Integer puntosLocal, Integer puntosVisitante) {

        Partido partido = partidoRepository.findById(id).orElseThrow();

        if (partido.getEstado() == EstadoPartido.FINALIZADO) {
            throw new RuntimeException("El partido ya está finalizado");
        }

        partido.setPuntosLocal(puntosLocal);
        partido.setPuntosVisitante(puntosVisitante);
        partido.setEstado(EstadoPartido.FINALIZADO);

        // Guardamos el partido antes de resolver apuestas para que el resultado sea persistente
        // y consistente si otros procesos consultan el estado durante la resolución.
        partidoRepository.save(partido);

        // Resolver apuestas es parte de la misma operación lógica: debe ser atómica con el cambio
        // de estado del partido para evitar partidos FINALIZADOS con apuestas aún PENDIENTES.
        apuestaService.resolverApuestas(partido);

        return partido;
    }
}
