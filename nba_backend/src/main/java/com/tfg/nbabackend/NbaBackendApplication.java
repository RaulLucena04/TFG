package com.tfg.nbabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot del backend de NBA Predictor.
 * 
 * <p>Esta aplicación proporciona una API REST para gestionar usuarios, partidos,
 * equipos, jugadores y apuestas del sistema de predicciones de la NBA.
 * 
 * @author TFG
 * @version 1.0
 */
@SpringBootApplication
public class NbaBackendApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args argumentos de la línea de comandos
     */
	public static void main(String[] args) {
		SpringApplication.run(NbaBackendApplication.class, args);
	}

}
