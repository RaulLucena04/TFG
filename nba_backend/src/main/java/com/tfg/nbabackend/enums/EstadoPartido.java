package com.tfg.nbabackend.enums;

/**
 * Enum que representa el estado de un partido de la NBA.
 * 
 * @author TFG
 * @version 1.0
 */
public enum EstadoPartido {
    /**
     * Partido programado que aún no se ha disputado.
     */
    PROGRAMADO,
    
    /**
     * Partido que ya se ha disputado y tiene resultado final.
     */
    FINALIZADO
}