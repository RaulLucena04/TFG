package com.tfg.nbabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;

/**
 * Clase principal de la aplicación Spring Boot del backend de NBA Predictor.
 *
 * <p>Arranca el contexto Spring (JPA, servicios, etc.) <b>sin</b> servidor web HTTP embebido
 * ({@link org.springframework.boot.WebApplicationType#NONE}): la exposición a clientes es un
 * <b>servidor TCP</b> propio (puerto {@code 9090} por defecto, configurable con {@code socket.port}),
 * con mensajes JSON en tramas length-prefixed; ver {@code com.tfg.nbabackend.socket}.</p>
 *
 * <p>MySQL se configura en {@code application.properties} ({@code spring.datasource.*}).</p>
 *
 * @author TFG
 * @version 1.0
 */
@SpringBootApplication
public class NbaBackendApplication {

    /**
     * Inicia el contexto Spring Boot (persistencia + servicios + servidor TCP de aplicación).
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(NbaBackendApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

}
