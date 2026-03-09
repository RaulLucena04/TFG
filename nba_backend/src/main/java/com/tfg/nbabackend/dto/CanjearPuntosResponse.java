package com.tfg.nbabackend.dto;

/**
 * Respuesta del canje de puntos (simulación PayPal).
 */
public class CanjearPuntosResponse {
    private boolean exito;
    private String mensaje;
    private double eurosTransferidos;
    private int puntosCanjeados;

    public CanjearPuntosResponse() {}

    public CanjearPuntosResponse(boolean exito, String mensaje, double eurosTransferidos, int puntosCanjeados) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.eurosTransferidos = eurosTransferidos;
        this.puntosCanjeados = puntosCanjeados;
    }

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public double getEurosTransferidos() { return eurosTransferidos; }
    public void setEurosTransferidos(double eurosTransferidos) { this.eurosTransferidos = eurosTransferidos; }
    public int getPuntosCanjeados() { return puntosCanjeados; }
    public void setPuntosCanjeados(int puntosCanjeados) { this.puntosCanjeados = puntosCanjeados; }
}
