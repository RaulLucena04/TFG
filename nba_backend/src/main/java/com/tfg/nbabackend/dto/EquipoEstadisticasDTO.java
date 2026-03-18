package com.tfg.nbabackend.dto;

/**
 * DTO con estadísticas calculadas de un equipo.
 * 
 * <p>Contiene:
 * <ul>
 *   <li>Record: victorias y derrotas basadas en partidos finalizados</li>
 *   <li>PPG (Puntos Por Partido): suma de promedios de puntos de jugadores</li>
 *   <li>RPG (Rebotes Por Partido): suma de promedios de rebotes de jugadores</li>
 *   <li>APG (Asistencias Por Partido): suma de promedios de asistencias de jugadores</li>
 * </ul>
 * 
 * @author TFG
 * @version 1.0
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
