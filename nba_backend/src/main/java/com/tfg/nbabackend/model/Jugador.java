package com.tfg.nbabackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String posicion; // Ej: Base, Escolta, Alero, Ala-pívot, Pívot

    private Double promedioPuntos; // estadísticas
    private Double promedioAsistencias;
    private Double promedioRebotes;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    // Constructor vacío para JPA
    public Jugador() {}

    // Constructor completo
    public Jugador(String nombre, String posicion, Double promedioPuntos, Double promedioAsistencias, Double promedioRebotes, Equipo equipo) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.promedioPuntos = promedioPuntos;
        this.promedioAsistencias = promedioAsistencias;
        this.promedioRebotes = promedioRebotes;
        this.equipo = equipo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    public Double getPromedioPuntos() { return promedioPuntos; }
    public void setPromedioPuntos(Double promedioPuntos) { this.promedioPuntos = promedioPuntos; }

    public Double getPromedioAsistencias() { return promedioAsistencias; }
    public void setPromedioAsistencias(Double promedioAsistencias) { this.promedioAsistencias = promedioAsistencias; }

    public Double getPromedioRebotes() { return promedioRebotes; }
    public void setPromedioRebotes(Double promedioRebotes) { this.promedioRebotes = promedioRebotes; }

    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }
}