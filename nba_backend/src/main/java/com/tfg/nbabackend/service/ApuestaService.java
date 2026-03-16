package com.tfg.nbabackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.nbabackend.enums.ResultadoApuesta;
import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.ApuestaRepository;
import com.tfg.nbabackend.repository.PartidoRepository;
import com.tfg.nbabackend.repository.UsuarioRepository;

/**
 * Servicio que gestiona las operaciones relacionadas con apuestas.
 * 
 * <p>Este servicio se encarga de crear apuestas, calcular cuotas, validar
 * que los usuarios tengan suficientes puntos y resolver apuestas cuando
 * se finalizan los partidos.
 * 
 * @author TFG
 * @version 1.0
 */
@Service
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PartidoRepository partidoRepository;
    private final EquipoEstadisticasService equipoEstadisticasService;

    /**
     * Constructor del servicio de apuestas.
     * 
     * @param apuestaRepository repositorio de apuestas
     * @param usuarioRepository repositorio de usuarios
     * @param partidoRepository repositorio de partidos
     * @param equipoEstadisticasService servicio de estadísticas de equipos
     */
    public ApuestaService(ApuestaRepository apuestaRepository,
            UsuarioRepository usuarioRepository,
            PartidoRepository partidoRepository,
            EquipoEstadisticasService equipoEstadisticasService) {
        this.apuestaRepository = apuestaRepository;
        this.usuarioRepository = usuarioRepository;
        this.partidoRepository = partidoRepository;
        this.equipoEstadisticasService = equipoEstadisticasService;
    }

    /**
     * Crea una nueva apuesta en el sistema.
     * 
     * <p>Valida que el usuario tenga suficientes puntos, calcula la cuota si no está
     * especificada, descuenta los puntos del usuario y guarda la apuesta con estado PENDIENTE.
     * 
     * @param apuesta la apuesta a crear
     * @return la apuesta creada y guardada
     * @throws RuntimeException si el usuario no existe, no tiene suficientes puntos o el partido no existe
     */
    public Apuesta crearApuesta(Apuesta apuesta) {

        Usuario usuario = usuarioRepository.findById(
                apuesta.getUsuario().getId()
        ).orElseThrow();

        if (usuario.getPuntos() < apuesta.getPuntosApostados()) {
            throw new RuntimeException("No tienes suficientes puntos");
        }

        if (apuesta.getCuota() == null || apuesta.getCuota() <= 0) {
            Partido partidoCompleto = apuesta.getPartido() != null && apuesta.getPartido().getId() != null
                    ? partidoRepository.findById(apuesta.getPartido().getId()).orElse(null)
                    : null;
            double cuota = calcularCuota(partidoCompleto, apuesta.getPrediccion());
            apuesta.setCuota(cuota);
        }

        usuario.setPuntos(usuario.getPuntos() - apuesta.getPuntosApostados());
        usuarioRepository.save(usuario);

        apuesta.setResultado(ResultadoApuesta.PENDIENTE);

        return apuestaRepository.save(apuesta);
    }

    /**
     * Calcula la cuota de una apuesta basándose en el historial de los equipos.
     * 
     * <p>La cuota se calcula considerando:
     * <ul>
     *   <li>El record de victorias/derrotas de cada equipo</li>
     *   <li>La ventaja de jugar en casa (aproximadamente +5% de probabilidad)</li>
     *   <li>La predicción del usuario (LOCAL o VISITANTE)</li>
     * </ul>
     * 
     * <p>Si un equipo tiene mejor record, la cuota será más baja al apostar por él.
     * La cuota base es 2.0 y se ajusta en un rango de 1.5 a 5.0.
     * 
     * @param partido el partido sobre el que se calcula la cuota
     * @param prediccion la predicción del usuario ("LOCAL" o "VISITANTE")
     * @return la cuota calculada (entre 1.5 y 5.0)
     */
    private double calcularCuota(Partido partido, String prediccion) {
        if (partido == null || partido.getEquipoLocal() == null || partido.getEquipoVisitante() == null) {
            return 2.0;
        }

        Long idLocal = partido.getEquipoLocal().getId();
        Long idVisitante = partido.getEquipoVisitante().getId();

        var statsLocal = equipoEstadisticasService.calcularEstadisticas(idLocal);
        var statsVisitante = equipoEstadisticasService.calcularEstadisticas(idVisitante);

        int vLocal = statsLocal.getVictorias();
        int dLocal = statsLocal.getDerrotas();
        int vVisitante = statsVisitante.getVictorias();
        int dVisitante = statsVisitante.getDerrotas();

        int totalLocal = vLocal + dLocal;
        int totalVisitante = vVisitante + dVisitante;
        double winRateLocal = totalLocal > 0 ? (double) vLocal / totalLocal : 0.5;
        double winRateVisitante = totalVisitante > 0 ? (double) vVisitante / totalVisitante : 0.5;

        // Diferencia de record: positivo si local es mejor
        double diferenciaRecord = winRateLocal - winRateVisitante;

        // Ventaja de jugar en casa (aprox. +5% probabilidad)
        final double VENTAJA_LOCAL = 0.05;

        double cuotaBase = 2.0;

        if ("LOCAL".equalsIgnoreCase(prediccion)) {
            // Apostar por local: si local es favorito, cuota baja; si visitante es mejor, cuota alta
            // diferenciaRecord > 0 → local mejor → cuota más baja
            double factor = -diferenciaRecord * 0.6 - VENTAJA_LOCAL;
            cuotaBase = 2.0 + factor;
        } else {
            // Apostar por visitante: si visitante es favorito, cuota baja
            double factor = diferenciaRecord * 0.6 + VENTAJA_LOCAL;
            cuotaBase = 2.0 + factor;
        }

        double cuota = Math.max(1.5, Math.min(5.0, cuotaBase));
        return Math.round(cuota * 100.0) / 100.0;
    }

    /**
     * Obtiene todas las apuestas realizadas por un usuario.
     * 
     * @param usuario el usuario del que se quieren obtener las apuestas
     * @return lista de apuestas del usuario
     */
    public List<Apuesta> obtenerPorUsuario(Usuario usuario) {
        return apuestaRepository.findByUsuario(usuario);
    }

    /**
     * Resuelve todas las apuestas relacionadas con un partido finalizado.
     * 
     * <p>Para cada apuesta pendiente del partido:
     * <ul>
     *   <li>Compara la predicción con el resultado real</li>
     *   <li>Si la apuesta es acertada, calcula las ganancias según la cuota y actualiza los puntos del usuario</li>
     *   <li>Si la apuesta es incorrecta, marca la apuesta como PERDIDA</li>
     *   <li>Actualiza el estado de la apuesta (GANADA o PERDIDA)</li>
     * </ul>
     * 
     * <p>Las apuestas ya resueltas se ignoran para evitar procesarlas dos veces.
     * 
     * @param partido el partido finalizado cuyas apuestas se deben resolver
     */
    public void resolverApuestas(Partido partido) {

        List<Apuesta> apuestas = apuestaRepository.findByPartido(partido);

        for (Apuesta apuesta : apuestas) {

            if (apuesta.getResultado() != ResultadoApuesta.PENDIENTE) {
                continue;
            }

            boolean ganaLocal = partido.getPuntosLocal() > partido.getPuntosVisitante();

            boolean acertada = false;

            if (ganaLocal && apuesta.getPrediccion().equalsIgnoreCase("LOCAL")) {
                acertada = true;
            }

            if (!ganaLocal && apuesta.getPrediccion().equalsIgnoreCase("VISITANTE")) {
                acertada = true;
            }

            if (acertada) {

                apuesta.setResultado(ResultadoApuesta.GANADA);

                Usuario usuario = apuesta.getUsuario();

                double cuota = apuesta.getCuota() != null ? apuesta.getCuota() : 2.0;
                int ganancia = (int) Math.round(apuesta.getPuntosApostados() * cuota);

                usuario.setPuntos(usuario.getPuntos() + ganancia);

                usuarioRepository.save(usuario);

            } else {
                apuesta.setResultado(ResultadoApuesta.PERDIDA);
            }

            apuestaRepository.save(apuesta);
        }
    }
}
