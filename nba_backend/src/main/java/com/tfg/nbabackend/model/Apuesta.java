package com.tfg.nbabackend.model;

import jakarta.persistence.*;

@Entity
public class Apuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int puntosApostados;

    private String prediccion;

    private Boolean acertada;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "partido_id")
    private Partido partido;

    // ----- GETTERS Y SETTERS -----

    public Long getId() {
        return id;
    }

    public int getPuntosApostados() {
        return puntosApostados;
    }

    public void setPuntosApostados(int puntosApostados) {
        this.puntosApostados = puntosApostados;
    }

    public String getPrediccion() {
        return prediccion;
    }

    public void setPrediccion(String prediccion) {
        this.prediccion = prediccion;
    }

    public Boolean getAcertada() {
        return acertada;
    }

    public void setAcertada(Boolean acertada) {
        this.acertada = acertada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}