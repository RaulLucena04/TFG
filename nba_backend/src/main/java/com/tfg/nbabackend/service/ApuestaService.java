package com.tfg.nbabackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.nbabackend.enums.ResultadoApuesta;
import com.tfg.nbabackend.model.Apuesta;
import com.tfg.nbabackend.model.Partido;
import com.tfg.nbabackend.model.Usuario;
import com.tfg.nbabackend.repository.ApuestaRepository;
import com.tfg.nbabackend.repository.UsuarioRepository;

@Service
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final UsuarioRepository usuarioRepository;

    public ApuestaService(ApuestaRepository apuestaRepository,
            UsuarioRepository usuarioRepository) {
        this.apuestaRepository = apuestaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Apuesta crearApuesta(Apuesta apuesta) {

        Usuario usuario = usuarioRepository.findById(
                apuesta.getUsuario().getId()
        ).orElseThrow();

        if (usuario.getPuntos() < apuesta.getPuntosApostados()) {
            throw new RuntimeException("No tienes suficientes puntos");
        }

        // Calcular cuota si no viene en la apuesta
        if (apuesta.getCuota() == null || apuesta.getCuota() <= 0) {
            double cuota = calcularCuota(apuesta.getPartido(), apuesta.getPrediccion());
            apuesta.setCuota(cuota);
        }

        // Restar puntos
        usuario.setPuntos(usuario.getPuntos() - apuesta.getPuntosApostados());
        usuarioRepository.save(usuario);

        // Estado inicial
        apuesta.setResultado(ResultadoApuesta.PENDIENTE);

        return apuestaRepository.save(apuesta);
    }

    /**
     * Calcula la cuota para una apuesta basándose en una lógica simple
     * Por defecto, las cuotas son equilibradas con un margen para la casa
     * @param partido El partido sobre el que se apuesta
     * @param prediccion "LOCAL" o "VISITANTE"
     * @return La cuota calculada (mínimo 1.5, máximo 5.0)
     */
    private double calcularCuota(Partido partido, String prediccion) {
        if (partido == null || partido.getEquipoLocal() == null || partido.getEquipoVisitante() == null) {
            // Cuota por defecto si no hay información
            return 2.0;
        }

        // Lógica simple: cuotas base equilibradas
        // En un partido equilibrado, cada equipo tiene ~50% de probabilidad
        // Cuota base = 1 / 0.5 = 2.0, con margen de casa = 2.0 * 0.95 = 1.9
        
        // Variación basada en el nombre del equipo (para dar algo de variabilidad)
        // En producción, esto se calcularía con estadísticas reales
        double variacion = 0.0;
        
        // Pequeña variación aleatoria basada en hash del nombre
        String nombreLocal = partido.getEquipoLocal().getNombre();
        String nombreVisitante = partido.getEquipoVisitante().getNombre();
        
        // Validar que los nombres no sean null
        if (nombreLocal == null) nombreLocal = "EquipoLocal";
        if (nombreVisitante == null) nombreVisitante = "EquipoVisitante";
        
        int hashLocal = nombreLocal.hashCode();
        int hashVisitante = nombreVisitante.hashCode();
        
        // Normalizar a rango -0.3 a +0.3
        double factorLocal = (hashLocal % 60 - 30) / 100.0;
        double factorVisitante = (hashVisitante % 60 - 30) / 100.0;
        
        if ("LOCAL".equalsIgnoreCase(prediccion)) {
            // Si apostamos por local, menor factor = mayor cuota (menos probable)
            variacion = -factorLocal;
        } else {
            // Si apostamos por visitante, menor factor = mayor cuota
            variacion = -factorVisitante;
        }
        
        // Cuota base con margen de casa (5%)
        double cuotaBase = 1.9;
        double cuota = cuotaBase + variacion;
        
        // Limitar cuota entre 1.5 y 5.0
        cuota = Math.max(1.5, Math.min(5.0, cuota));
        
        // Redondear a 2 decimales
        return Math.round(cuota * 100.0) / 100.0;
    }

    public List<Apuesta> obtenerPorUsuario(Usuario usuario) {
        return apuestaRepository.findByUsuario(usuario);
    }

    public void resolverApuestas(Partido partido) {

        List<Apuesta> apuestas = apuestaRepository.findByPartido(partido);

        for (Apuesta apuesta : apuestas) {

            // ⚠️ Evitar resolver dos veces
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

                // Calcular ganancia basada en la cuota
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
