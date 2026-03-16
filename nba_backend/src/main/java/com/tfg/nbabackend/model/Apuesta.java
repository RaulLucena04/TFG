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

/**
 * Entidad que representa una apuesta realizada por un usuario sobre un partido.
 * 
 * <p>Una apuesta contiene la predicción del usuario (equipo local o visitante),
 * los puntos apostados, la cuota calculada y el resultado final (PENDIENTE, GANADA o PERDIDA).
 * 
 * @author TFG
 * @version 1.0
 */
@Entity
public class Apuesta {

    /**
     * Identificador único de la apuesta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cantidad de puntos virtuales apostados por el usuario.
     */
    private int puntosApostados;

    /**
     * Predicción del usuario: "LOCAL" o "VISITANTE".
     */
    private String prediccion;

    /**
     * Cuota de la apuesta. Indica el multiplicador de ganancia si la apuesta es acertada.
     * Por ejemplo, una cuota de 2.5 significa que si ganas, recibes 2.5x lo apostado.
     */
    @Column(nullable = true)
    private Double cuota;

    /**
     * Resultado de la apuesta: PENDIENTE, GANADA o PERDIDA.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoApuesta resultado;

    /**
     * Usuario que realizó la apuesta.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Partido sobre el que se realiza la apuesta.
     */
    @ManyToOne
    @JoinColumn(name = "partido_id")
    private Partido partido;

    /**
     * Obtiene el identificador único de la apuesta.
     * 
     * @return el ID de la apuesta
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la apuesta.
     * 
     * @param id el ID de la apuesta
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene los puntos apostados.
     * 
     * @return los puntos apostados
     */
    public int getPuntosApostados() {
        return puntosApostados;
    }

    /**
     * Establece los puntos apostados.
     * 
     * @param puntosApostados los puntos apostados
     */
    public void setPuntosApostados(int puntosApostados) {
        this.puntosApostados = puntosApostados;
    }

    /**
     * Obtiene la predicción del usuario.
     * 
     * @return la predicción ("LOCAL" o "VISITANTE")
     */
    public String getPrediccion() {
        return prediccion;
    }

    /**
     * Establece la predicción del usuario.
     * 
     * @param prediccion la predicción ("LOCAL" o "VISITANTE")
     */
    public void setPrediccion(String prediccion) {
        this.prediccion = prediccion;
    }

    /**
     * Obtiene la cuota de la apuesta.
     * 
     * @return la cuota, o null si no está calculada
     */
    public Double getCuota() {
        return cuota;
    }

    /**
     * Establece la cuota de la apuesta.
     * 
     * @param cuota la cuota de la apuesta
     */
    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }

    /**
     * Obtiene el resultado de la apuesta.
     * 
     * @return el resultado (PENDIENTE, GANADA o PERDIDA)
     */
    public ResultadoApuesta getResultado() {
        return resultado;
    }

    /**
     * Establece el resultado de la apuesta.
     * 
     * @param resultado el resultado (PENDIENTE, GANADA o PERDIDA)
     */
    public void setResultado(ResultadoApuesta resultado) {
        this.resultado = resultado;
    }

    /**
     * Obtiene el usuario que realizó la apuesta.
     * 
     * @return el usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que realizó la apuesta.
     * 
     * @param usuario el usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el partido sobre el que se realiza la apuesta.
     * 
     * @return el partido
     */
    public Partido getPartido() {
        return partido;
    }

    /**
     * Establece el partido sobre el que se realiza la apuesta.
     * 
     * @param partido el partido
     */
    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}