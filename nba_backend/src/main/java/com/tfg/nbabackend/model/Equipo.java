package com.tfg.nbabackend.model;

import jakarta.persistence.*;

/**
 * Entidad que representa un equipo de la NBA.
 * 
 * <p>Un equipo pertenece a una conferencia (Este u Oeste) y a una división.
 * Los equipos participan en partidos y tienen jugadores asociados.
 * 
 * @author TFG
 * @version 1.0
 */
@Entity
public class Equipo {

    /**
     * Identificador único del equipo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del equipo.
     */
    private String nombre;

    /**
     * Conferencia a la que pertenece el equipo (Este u Oeste).
     */
    private String conferencia;

    /**
     * División a la que pertenece el equipo dentro de su conferencia.
     */
    private String division;

    /**
     * Obtiene el identificador único del equipo.
     * 
     * @return el ID del equipo
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del equipo.
     * 
     * @param id el ID del equipo
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del equipo.
     * 
     * @return el nombre del equipo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del equipo.
     * 
     * @param nombre el nombre del equipo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la conferencia del equipo.
     * 
     * @return la conferencia (Este u Oeste)
     */
    public String getConferencia() {
        return conferencia;
    }

    /**
     * Establece la conferencia del equipo.
     * 
     * @param conferencia la conferencia (Este u Oeste)
     */
    public void setConferencia(String conferencia) {
        this.conferencia = conferencia;
    }

    /**
     * Obtiene la división del equipo.
     * 
     * @return la división
     */
    public String getDivision() {
        return division;
    }

    /**
     * Establece la división del equipo.
     * 
     * @param division la división
     */
    public void setDivision(String division) {
        this.division = division;
    }
}