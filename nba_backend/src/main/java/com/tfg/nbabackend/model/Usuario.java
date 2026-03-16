package com.tfg.nbabackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

/**
 * Entidad que representa un usuario del sistema de predicciones NBA.
 * 
 * <p>Un usuario puede realizar apuestas, acumular puntos virtuales y competir
 * en rankings. Los usuarios pueden tener dos roles: USER (usuario normal) o ADMIN (administrador).
 * 
 * @author TFG
 * @version 1.0
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único utilizado para el login.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Correo electrónico único del usuario.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Contraseña hasheada del usuario (no se serializa en JSON).
     */
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Puntos virtuales del usuario para realizar apuestas.
     */
    private int puntos;

    /**
     * Rol del usuario (USER o ADMIN).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    /**
     * Lista de apuestas realizadas por el usuario.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Apuesta> apuestas;

    /**
     * Obtiene el identificador único del usuario.
     * 
     * @return el ID del usuario
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario.
     * 
     * @param id el ID del usuario
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return el nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     * 
     * @param username el nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * 
     * @return el correo electrónico
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     * 
     * @param email el correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña hasheada del usuario.
     * 
     * @return la contraseña hasheada
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario (debe estar hasheada).
     * 
     * @param password la contraseña hasheada
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene los puntos virtuales del usuario.
     * 
     * @return los puntos del usuario
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Establece los puntos virtuales del usuario.
     * 
     * @param puntos los puntos del usuario
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Obtiene el rol del usuario.
     * 
     * @return el rol del usuario (USER o ADMIN)
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * 
     * @param rol el rol del usuario (USER o ADMIN)
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la lista de apuestas realizadas por el usuario.
     * 
     * @return la lista de apuestas
     */
    public List<Apuesta> getApuestas() {
        return apuestas;
    }

    /**
     * Establece la lista de apuestas del usuario.
     * 
     * @param apuestas la lista de apuestas
     */
    public void setApuestas(List<Apuesta> apuestas) {
        this.apuestas = apuestas;
    }
}
