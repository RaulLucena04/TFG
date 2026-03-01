package com.tfg.nbabackend.model;

import com.tfg.nbabackend.enums.ResultadoApuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Apuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int puntosApostados;

    private String prediccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoApuesta resultado;

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

    public ResultadoApuesta getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoApuesta resultado) {
        this.resultado = resultado;
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