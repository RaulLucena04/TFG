package com.tfg.nbabackend.model;

import jakarta.persistence.*;

/**
 * Entidad que representa un jugador de la NBA.
 * 
 * <p>Un jugador pertenece a un equipo y tiene estadísticas promedio
 * de puntos, asistencias y rebotes por partido.
 * 
 * @author TFG
 * @version 1.0
 */
@Entity
@Table(name = "jugadores")
public class Jugador {

    /**
     * Identificador único del jugador.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del jugador.
     */
    private String nombre;

    /**
     * Posición del jugador en la cancha (Base, Escolta, Alero, Ala-pívot, Pívot).
     */
    private String posicion;

    /**
     * Promedio de puntos por partido del jugador.
     */
    private Double promedioPuntos;

    /**
     * Promedio de asistencias por partido del jugador.
     */
    private Double promedioAsistencias;

    /**
     * Promedio de rebotes por partido del jugador.
     */
    private Double promedioRebotes;

    /**
     * Equipo al que pertenece el jugador.
     */
    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Jugador() {}

    /**
     * Constructor completo para crear un jugador con todos sus datos.
     * 
     * @param nombre el nombre del jugador
     * @param posicion la posición del jugador
     * @param promedioPuntos el promedio de puntos por partido
     * @param promedioAsistencias el promedio de asistencias por partido
     * @param promedioRebotes el promedio de rebotes por partido
     * @param equipo el equipo al que pertenece
     */
    public Jugador(String nombre, String posicion, Double promedioPuntos, Double promedioAsistencias, Double promedioRebotes, Equipo equipo) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.promedioPuntos = promedioPuntos;
        this.promedioAsistencias = promedioAsistencias;
        this.promedioRebotes = promedioRebotes;
        this.equipo = equipo;
    }

    /**
     * Obtiene el identificador único del jugador.
     * 
     * @return el ID del jugador
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del jugador.
     * 
     * @param id el ID del jugador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return el nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del jugador.
     * 
     * @param nombre el nombre del jugador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la posición del jugador.
     * 
     * @return la posición del jugador
     */
    public String getPosicion() {
        return posicion;
    }

    /**
     * Establece la posición del jugador.
     * 
     * @param posicion la posición del jugador
     */
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    /**
     * Obtiene el promedio de puntos por partido.
     * 
     * @return el promedio de puntos
     */
    public Double getPromedioPuntos() {
        return promedioPuntos;
    }

    /**
     * Establece el promedio de puntos por partido.
     * 
     * @param promedioPuntos el promedio de puntos
     */
    public void setPromedioPuntos(Double promedioPuntos) {
        this.promedioPuntos = promedioPuntos;
    }

    /**
     * Obtiene el promedio de asistencias por partido.
     * 
     * @return el promedio de asistencias
     */
    public Double getPromedioAsistencias() {
        return promedioAsistencias;
    }

    /**
     * Establece el promedio de asistencias por partido.
     * 
     * @param promedioAsistencias el promedio de asistencias
     */
    public void setPromedioAsistencias(Double promedioAsistencias) {
        this.promedioAsistencias = promedioAsistencias;
    }

    /**
     * Obtiene el promedio de rebotes por partido.
     * 
     * @return el promedio de rebotes
     */
    public Double getPromedioRebotes() {
        return promedioRebotes;
    }

    /**
     * Establece el promedio de rebotes por partido.
     * 
     * @param promedioRebotes el promedio de rebotes
     */
    public void setPromedioRebotes(Double promedioRebotes) {
        this.promedioRebotes = promedioRebotes;
    }

    /**
     * Obtiene el equipo al que pertenece el jugador.
     * 
     * @return el equipo del jugador
     */
    public Equipo getEquipo() {
        return equipo;
    }

    /**
     * Establece el equipo al que pertenece el jugador.
     * 
     * @param equipo el equipo del jugador
     */
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}