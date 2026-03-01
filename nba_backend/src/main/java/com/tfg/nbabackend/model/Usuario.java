package com.tfg.nbabackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private int puntos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;   // 👈 NUEVO CAMPO

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Apuesta> apuestas;

    // ----- GETTERS Y SETTERS -----

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setId(Long id) { this.id = id; }

    public void setPassword(String password) { this.password = password; }

    public List<Apuesta> getApuestas() { return apuestas; }

    public void setApuestas(List<Apuesta> apuestas) { this.apuestas = apuestas; }

    public int getPuntos() { return puntos; }

    public void setPuntos(int puntos) { this.puntos = puntos; }

    public String getPassword() { return password; }

    public Rol getRol() { return rol; }

    public void setRol(Rol rol) { this.rol = rol; }
}