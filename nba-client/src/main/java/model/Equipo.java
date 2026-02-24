package model;

import java.util.List;

public class Equipo {
    private Long id;
    private String nombre;
    private String conferencia;
    private String division;
    private int victorias;
    private int derrotas;
    private double ppg;
    private double rpg;
    private double apg;

    private List<Jugador> jugadores; // lista de jugadores
    private List<Partido> partidosRecientes; // lista de partidos

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getConferencia() { return conferencia; }
    public void setConferencia(String conferencia) { this.conferencia = conferencia; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

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

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    public List<Partido> getPartidosRecientes() { return partidosRecientes; }
    public void setPartidosRecientes(List<Partido> partidosRecientes) { this.partidosRecientes = partidosRecientes; }
    
}