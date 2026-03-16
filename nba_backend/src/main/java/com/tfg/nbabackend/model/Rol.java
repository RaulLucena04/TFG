package com.tfg.nbabackend.model;

/**
 * Enum que representa los roles de usuario en el sistema.
 * 
 * @author TFG
 * @version 1.0
 */
public enum Rol {
    /**
     * Usuario normal que puede realizar apuestas y consultar estadísticas.
     */
    USER,
    
    /**
     * Administrador que tiene acceso al panel de administración para gestionar
     * partidos, equipos, jugadores y usuarios.
     */
    ADMIN
}