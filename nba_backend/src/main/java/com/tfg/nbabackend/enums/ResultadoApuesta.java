package com.tfg.nbabackend.enums;

/**
 * Enum que representa el resultado de una apuesta.
 * 
 * @author TFG
 * @version 1.0
 */
public enum ResultadoApuesta {
    /**
     * Apuesta pendiente de resolución (el partido aún no se ha finalizado).
     */
    PENDIENTE,
    
    /**
     * Apuesta ganada (la predicción del usuario fue correcta).
     */
    GANADA,
    
    /**
     * Apuesta perdida (la predicción del usuario fue incorrecta).
     */
    PERDIDA
}