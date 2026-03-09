package com.tfg.nbabackend.dto;

/**
 * DTO con estadísticas calculadas de un equipo: record y promedios.
 */
public class EquipoEstadisticasDTO {
    private int victorias;
    private int derrotas;
    private double ppg;
    private double rpg;
    private double apg;

    public EquipoEstadisticasDTO() {}

    public EquipoEstadisticasDTO(int victorias, int derrotas, double ppg, double rpg, double apg) {
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.ppg = ppg;
        this.rpg = rpg;
        this.apg = apg;
    }

    public int getVictorias() { return victorias; }
    public void setVictorias(int victorias) { this.victorias = victorias; }
    public int getDerrotas() { return derrotas; }
    public void setDerrotas(int derrotas) { this.derrotas = derrotas; }
    public double getPpg() { return ppg; }
    public void setPpg(double ppg) { this.ppg = ppg; }
    public double getRpg() { return rpg; }
    public void setRpg(double rpg) { this.rpg = rpg; }
    public double getApg() { return apg; }
    public void setApg(double apg) { this.apg = apg; }
}
