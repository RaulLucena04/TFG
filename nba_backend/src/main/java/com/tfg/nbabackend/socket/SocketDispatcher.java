package com.tfg.nbabackend.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.nbabackend.dto.CanjearPuntosRequest;
import com.tfg.nbabackend.dto.CanjearPuntosResponse;
import com.tfg.nbabackend.dto.EquipoConEstadisticasDTO;
import com.tfg.nbabackend.dto.EquipoEstadisticasDTO;
import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Equipo;
import com.tfg.nbabackend.model.Jugador;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.service.ApuestaService;
import com.tfg.nbabackend.service.EquipoEstadisticasService;
import com.tfg.nbabackend.service.EquipoService;
import com.tfg.nbabackend.service.JugadorService;
import com.tfg.nbabackend.service.PartidoService;
import com.tfg.nbabackend.service.TiendaService;
import com.tfg.nbabackend.service.UsuarioService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Router (dispatcher) del protocolo TCP del backend.
 *
 * <h2>Flujo completo (vista rápida)</h2>
 * <ol>
 *   <li>El cliente envía un JSON con {@code requestId}, {@code action} y {@code payload}.</li>
 *   <li>{@link SocketServerRunner} lee el frame, parsea el JSON a {@link SocketRequest}.</li>
 *   <li>Este dispatcher mira {@code action} y llama al servicio correspondiente.</li>
 *   <li>Devuelve un {@link SocketResponse} con {@code ok=true,data=...} o {@code ok=false,error=...}.</li>
 *   <li>{@link SocketServerRunner} serializa la respuesta a JSON y la envía como frame.</li>
 * </ol>
 *
 * <h2>Convenciones</h2>
 * <ul>
 *   <li><b>Nombres de acciones</b>: {@code "dominio.operacion"} (ej. {@code "match.finalize"}).</li>
 *   <li><b>Payload</b>: es JSON arbitrario; aquí se convierte a entidades/DTO usando Jackson.</li>
 *   <li><b>Errores</b>: se capturan y se convierten a {@code ok=false} (sin reventar el socket).</li>
 * </ul>
 *
 * <p><b>Nota de diseño:</b> este protocolo es estrictamente request/response. No hay “push” de eventos
 * desde servidor a clientes conectados. Si un cliente necesita refrescar datos (ranking/puntos) tras una
 * acción (ej. {@code match.finalize}), debe hacer otra petición como {@code user.list} o {@code user.get}.</p>
 */
@Component
public class SocketDispatcher {
    private final ObjectMapper mapper;
    private final UsuarioService usuarioService;
    private final PartidoService partidoService;
    private final EquipoService equipoService;
    private final JugadorService jugadorService;
    private final ApuestaService apuestaService;
    private final TiendaService tiendaService;
    private final EquipoEstadisticasService equipoEstadisticasService;

    public SocketDispatcher(
            ObjectMapper mapper,
            UsuarioService usuarioService,
            PartidoService partidoService,
            EquipoService equipoService,
            JugadorService jugadorService,
            ApuestaService apuestaService,
            TiendaService tiendaService,
            EquipoEstadisticasService equipoEstadisticasService
    ) {
        this.mapper = mapper;
        this.usuarioService = usuarioService;
        this.partidoService = partidoService;
        this.equipoService = equipoService;
        this.jugadorService = jugadorService;
        this.apuestaService = apuestaService;
        this.tiendaService = tiendaService;
        this.equipoEstadisticasService = equipoEstadisticasService;
    }

    public SocketResponse handle(SocketRequest req) {
        String requestId = req != null ? req.requestId : null;
        if (req == null || req.action == null || req.action.isBlank()) {
            return SocketResponse.error(requestId, "Petición inválida: falta action");
        }

        try {
            return switch (req.action) {
                // ===== Usuarios =====
                case "user.register" -> SocketResponse.ok(requestId,
                        usuarioService.guardarUsuario(read(req.payload, Usuario.class)));
                case "user.login" -> handleLogin(requestId, req.payload);
                case "user.list" -> {
                    // El cliente usa esto para rankings: ordenamos por puntos en servidor.
                    List<Usuario> usuarios = usuarioService.obtenerTodos().stream()
                            .sorted(Comparator.comparingInt(Usuario::getPuntos).reversed())
                            .toList();
                    yield SocketResponse.ok(requestId, usuarios);
                }
                case "user.get" -> {
                    Long id = readLongField(req.payload, "id");
                    yield usuarioService.obtenerPorId(id)
                            .<SocketResponse>map(u -> SocketResponse.ok(requestId, u))
                            .orElseGet(() -> SocketResponse.error(requestId, "Usuario no encontrado"));
                }
                case "user.update" -> SocketResponse.ok(requestId,
                        usuarioService.actualizarUsuario(read(req.payload, Usuario.class)));
                case "user.changePassword" -> {
                    // Se espera payload: { "id": <long>, "password": "<string>" }
                    Long id = readLongField(req.payload, "id");
                    String password = readStringField(req.payload, "password");
                    usuarioService.cambiarPassword(id, password);
                    yield SocketResponse.ok(requestId, Map.of("message", "Contraseña actualizada"));
                }

                // ===== Partidos =====
                case "match.list" -> SocketResponse.ok(requestId, partidoService.listarPartidos());
                case "match.create" -> SocketResponse.ok(requestId,
                        partidoService.guardar(readPartidoForCreate(req.payload)));
                case "match.finalize" -> {
                    // Nota: al finalizar se resuelven apuestas y se actualizan puntos de usuarios en BD.
                    // Este protocolo es request/response (sin push), así que si el cliente necesita reflejar
                    // el nuevo saldo/ranking, debe pedirlo explícitamente (p.ej. "user.get" o "user.list").
                    Long id = readLongField(req.payload, "id");
                    Integer puntosLocal = readIntField(req.payload, "puntosLocal");
                    Integer puntosVisitante = readIntField(req.payload, "puntosVisitante");
                    yield SocketResponse.ok(requestId, partidoService.finalizarPartido(id, puntosLocal, puntosVisitante));
                }
                case "match.byTeam" -> {
                    Long equipoId = readLongField(req.payload, "equipoId");
                    List<Partido> partidos = partidoService.listarPartidos().stream()
                            .filter(p -> p.getEquipoLocal() != null && p.getEquipoVisitante() != null)
                            .filter(p -> equipoId.equals(p.getEquipoLocal().getId()) || equipoId.equals(p.getEquipoVisitante().getId()))
                            .toList();
                    yield SocketResponse.ok(requestId, partidos);
                }

                // ===== Equipos =====
                case "team.list" -> SocketResponse.ok(requestId, equipoService.listarTodos());
                case "team.get" -> {
                    Long id = readLongField(req.payload, "id");
                    yield equipoService.obtenerPorId(id)
                            .<SocketResponse>map(e -> SocketResponse.ok(requestId, e))
                            .orElseGet(() -> SocketResponse.error(requestId, "Equipo no encontrado"));
                }
                case "team.save" -> SocketResponse.ok(requestId,
                        equipoService.guardar(read(req.payload, Equipo.class)));
                case "team.stats" -> {
                    Long equipoId = readLongField(req.payload, "equipoId");
                    EquipoEstadisticasDTO stats = equipoEstadisticasService.calcularEstadisticas(equipoId);
                    yield SocketResponse.ok(requestId, stats);
                }
                case "team.withStats" -> {
                    // Conveniencia: devuelve equipos + estadísticas agregadas en una sola respuesta.
                    List<Equipo> equipos = equipoService.listarTodos();
                    List<EquipoConEstadisticasDTO> out = equipos.stream().map(eq -> {
                        EquipoEstadisticasDTO s = equipoEstadisticasService.calcularEstadisticas(eq.getId());
                        return new EquipoConEstadisticasDTO(
                                eq.getId(),
                                eq.getNombre(),
                                eq.getConferencia(),
                                eq.getDivision(),
                                s.getVictorias(),
                                s.getDerrotas(),
                                s.getPpg(),
                                s.getRpg(),
                                s.getApg()
                        );
                    }).toList();
                    yield SocketResponse.ok(requestId, out);
                }

                // ===== Jugadores =====
                case "player.list" -> SocketResponse.ok(requestId, jugadorService.listarTodos());
                case "player.byTeam" -> {
                    Long equipoId = readLongField(req.payload, "equipoId");
                    yield SocketResponse.ok(requestId, jugadorService.obtenerPorEquipo(equipoId));
                }
                case "player.save" -> SocketResponse.ok(requestId,
                        jugadorService.guardar(read(req.payload, Jugador.class)));

                // ===== Apuestas =====
                case "bet.create" -> SocketResponse.ok(requestId,
                        apuestaService.crearApuesta(read(req.payload, Apuesta.class)));
                case "bet.byUser" -> {
                    // El repositorio busca por entidad Usuario (al menos con id relleno).
                    Long userId = readLongField(req.payload, "userId");
                    Usuario u = new Usuario();
                    u.setId(userId);
                    yield SocketResponse.ok(requestId, apuestaService.obtenerPorUsuario(u));
                }

                // ===== Tienda =====
                case "store.redeem" -> {
                    CanjearPuntosRequest request = read(req.payload, CanjearPuntosRequest.class);
                    CanjearPuntosResponse resp = tiendaService.canjearPuntos(request);
                    yield SocketResponse.ok(requestId, resp);
                }

                default -> SocketResponse.error(requestId, "Acción no soportada: " + req.action);
            };
        } catch (Exception e) {
            // Cualquier excepción de negocio/parsing la devolvemos al cliente como ok=false.
            // Esto mantiene el socket vivo y permite al cliente manejar el error.
            String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            return SocketResponse.error(requestId, msg);
        }
    }

    private SocketResponse handleLogin(String requestId, JsonNode payload) {
        String username = readStringField(payload, "username");
        String password = readStringField(payload, "password");
        Usuario u = usuarioService.login(username, password);
        if (u == null) {
            return SocketResponse.error(requestId, "Credenciales incorrectas");
        }
        return SocketResponse.ok(requestId, u);
    }

    private <T> T read(JsonNode node, Class<T> cls) {
        if (node == null || node.isNull()) {
            // Permitimos payload vacío: evita NPEs en clientes que envían null/{}.
            return mapper.convertValue(Map.of(), cls);
        }
        return mapper.convertValue(node, cls);
    }

    private static String readStringField(JsonNode node, String field) {
        JsonNode v = node != null ? node.get(field) : null;
        if (v == null || v.isNull()) return null;
        return v.asText();
    }

    private static Long readLongField(JsonNode node, String field) {
        JsonNode v = node != null ? node.get(field) : null;
        if (v == null || v.isNull()) return null;
        return v.asLong();
    }

    private static Integer readIntField(JsonNode node, String field) {
        JsonNode v = node != null ? node.get(field) : null;
        if (v == null || v.isNull()) return null;
        return v.asInt();
    }

    private Partido readPartidoForCreate(JsonNode payload) {
        // Acepta payloads desde distintos clientes:
        // - Desktop: fecha como "YYYY-MM-DD" (LocalDate)
        // - Android: fecha como LocalDateTime (ISO-8601)
        Partido p = read(payload, Partido.class);

        // Crear vs actualizar:
        // - En el backend, un partido nuevo debe venir con id=null (porque el id lo genera MySQL).
        // - El cliente JavaFX usa LongProperty y su valor por defecto es 0, lo que acaba serializándose
        //   como "id": 0. Si lo dejamos pasar, Hibernate lo interpreta como un UPDATE del id=0 y falla
        //   con "Row was already updated or deleted...". Normalizamos 0 → null para que sea INSERT.
        if (p.getId() != null && p.getId() == 0L) {
            p.setId(null);
        }

        // Normalizar estado (desktop envía "Programado"/"Finalizado")
        if (p.getEstado() == null) {
            String estadoRaw = readStringField(payload, "estado");
            if (estadoRaw != null) {
                String up = estadoRaw.trim().toUpperCase();
                if (up.startsWith("PROG")) {
                    p.setEstado(com.tfg.nbabackend.enums.EstadoPartido.PROGRAMADO);
                } else if (up.startsWith("FIN")) {
                    p.setEstado(com.tfg.nbabackend.enums.EstadoPartido.FINALIZADO);
                }
            }
        }
        if (p.getEstado() == null) {
            p.setEstado(com.tfg.nbabackend.enums.EstadoPartido.PROGRAMADO);
        }

        // Normalizar fecha si viene como LocalDate (string sin hora)
        if (p.getFecha() == null) {
            JsonNode fechaNode = payload != null ? payload.get("fecha") : null;
            if (fechaNode != null && !fechaNode.isNull()) {
                String s = fechaNode.asText(null);
                if (s != null && !s.isBlank()) {
                    // Si es solo fecha (YYYY-MM-DD), usar mediodía para evitar zona horaria rara.
                    if (s.length() == 10 && s.charAt(4) == '-' && s.charAt(7) == '-') {
                        p.setFecha(java.time.LocalDate.parse(s).atTime(12, 0));
                    }
                }
            }
        }
        if (p.getFecha() == null) {
            p.setFecha(java.time.LocalDateTime.now());
        }

        return p;
    }
}

