package com.tfg.nbabackend.model;

import jakarta.persistence.*;

@Entity
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String conferencia;
    private String division;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getConferencia() {
        return conferencia;
    }
    public void setConferencia(String conferencia) {
        this.conferencia = conferencia;
    }
    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }

    // getters y setters
}