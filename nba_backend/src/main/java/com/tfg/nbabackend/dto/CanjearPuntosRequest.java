package com.tfg.nbabackend.dto;

/**
 * DTO para solicitud de canje de puntos por dinero mediante PayPal.
 * 
 * <p>Contiene la información necesaria para procesar un canje de puntos:
 * el ID del usuario, la cantidad de puntos a canjear y el email de PayPal
 * del destinatario de la transferencia.
 * 
 * @author TFG
 * @version 1.0
 */
public class CanjearPuntosRequest {
    private Long usuarioId;
    private int puntos;
    private String emailPayPal;

    public CanjearPuntosRequest() {}

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
    public String getEmailPayPal() { return emailPayPal; }
    public void setEmailPayPal(String emailPayPal) { this.emailPayPal = emailPayPal; }
}
