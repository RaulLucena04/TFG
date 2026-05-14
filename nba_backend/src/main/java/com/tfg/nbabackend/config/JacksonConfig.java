package com.tfg.nbabackend.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Jackson para el backend.
 *
 * <p>Registra módulos necesarios (por ejemplo, Java Time) y endurece la compatibilidad
 * con distintos clientes deshabilitando el fallo por propiedades desconocidas.</p>
 */
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // Permite que clientes envíen campos extra (p.ej. getters auxiliares como admin, gananciaPotencial, etc.)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}

