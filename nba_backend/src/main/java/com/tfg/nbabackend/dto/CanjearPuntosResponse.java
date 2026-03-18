package com.tfg.nbabackend.dto;

/**
 * DTO para respuesta de canje de puntos.
 * 
 * <p>Contiene el resultado del proceso de canje de puntos, incluyendo
 * si fue exitoso, el mensaje descriptivo, la cantidad en euros transferida
 * y la cantidad de puntos canjeados.
 * 
 * @author TFG
 * @version 1.0
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
