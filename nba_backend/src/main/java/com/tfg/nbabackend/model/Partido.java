package com.tfg.nbabackend.model;

import com.tfg.nbabackend.enums.EstadoPartido;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa un partido de la NBA.
 * 
 * <p>Un partido puede estar en estado PROGRAMADO (aún no se ha disputado)
 * o FINALIZADO (ya se ha jugado y tiene resultado). Los partidos programados
 * permiten realizar apuestas, mientras que los finalizados resuelven las apuestas.
 * 
 * @author TFG
 * @version 1.0
 */
@Entity
public class Partido {

    /**
     * Identificador único del partido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fecha y hora en la que se disputa el partido.
     */
    private LocalDateTime fecha;

    /**
     * Equipo que juega en casa (equipo local).
     */
    @ManyToOne
    @JoinColumn(name = "equipo_local_id")
    private Equipo equipoLocal;

    /**
     * Equipo que juega como visitante.
     */
    @ManyToOne
    @JoinColumn(name = "equipo_visitante_id")
    private Equipo equipoVisitante;

    /**
     * Puntos anotados por el equipo local (null si el partido no está finalizado).
     */
    private Integer puntosLocal;

    /**
     * Puntos anotados por el equipo visitante (null si el partido no está finalizado).
     */
    private Integer puntosVisitante;

    /**
     * Estado del partido (PROGRAMADO o FINALIZADO).
     */
    @Enumerated(EnumType.STRING)
    private EstadoPartido estado;

    /**
     * Obtiene el identificador único del partido.
     * 
     * @return el ID del partido
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del partido.
     * 
     * @param id el ID del partido
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha y hora del partido.
     * 
     * @return la fecha y hora del partido
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha y hora del partido.
     * 
     * @param fecha la fecha y hora del partido
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el equipo local (que juega en casa).
     * 
     * @return el equipo local
     */
    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    /**
     * Establece el equipo local.
     * 
     * @param equipoLocal el equipo local
     */
    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    /**
     * Obtiene el equipo visitante.
     * 
     * @return el equipo visitante
     */
    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    /**
     * Establece el equipo visitante.
     * 
     * @param equipoVisitante el equipo visitante
     */
    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    /**
     * Obtiene los puntos del equipo local.
     * 
     * @return los puntos del equipo local, o null si el partido no está finalizado
     */
    public Integer getPuntosLocal() {
        return puntosLocal;
    }

    /**
     * Establece los puntos del equipo local.
     * 
     * @param puntosLocal los puntos del equipo local
     */
    public void setPuntosLocal(Integer puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    /**
     * Obtiene los puntos del equipo visitante.
     * 
     * @return los puntos del equipo visitante, o null si el partido no está finalizado
     */
    public Integer getPuntosVisitante() {
        return puntosVisitante;
    }

    /**
     * Establece los puntos del equipo visitante.
     * 
     * @param puntosVisitante los puntos del equipo visitante
     */
    public void setPuntosVisitante(Integer puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }

    /**
     * Obtiene el estado del partido.
     * 
     * @return el estado del partido (PROGRAMADO o FINALIZADO)
     */
    public EstadoPartido getEstado() {
        return estado;
    }

    /**
     * Establece el estado del partido.
     * 
     * @param estado el estado del partido (PROGRAMADO o FINALIZADO)
     */
    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }
}