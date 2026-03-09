package com.tfg.nbabackend.dto;

/**
 * Request para canjear puntos por dinero vía PayPal (simulado).
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
