package com.tfg.nbabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot del backend de NBA Predictor.
 * 
 * <p>Esta aplicación proporciona una API REST para gestionar usuarios, partidos,
 * equipos, jugadores y apuestas del sistema de predicciones de la NBA.
 * 
 * <p>El servidor se ejecuta por defecto en el puerto 8080 y escucha en todas las
 * interfaces de red (0.0.0.0) para permitir conexiones desde diferentes máquinas.
 * Esto permite que tanto el cliente Java como la aplicación Android se conecten
 * al servidor desde la misma red local.
 * 
 * <p>Configuración (en application.properties):
 * <ul>
 *   <li>Base de datos: MySQL</li>
 *   <li>Puerto: 8080 (configurable)</li>
 *   <li>Dirección: 0.0.0.0 (escucha en todas las interfaces)</li>
 *   <li>CORS: Habilitado para todos los orígenes</li>
 * </ul>
 * 
 * @author TFG
 * @version 1.0
 */
@SpringBootApplication
public class NbaBackendApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * <p>Inicia el servidor Spring Boot que proporciona la API REST del sistema.
     * 
     * @param args argumentos de línea de comandos (no utilizados)
     */
	public static void main(String[] args) {
		SpringApplication.run(NbaBackendApplication.class, args);
	}

}
