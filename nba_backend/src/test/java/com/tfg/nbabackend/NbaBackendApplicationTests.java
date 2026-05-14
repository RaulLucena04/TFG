package com.tfg.nbabackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test mínimo: valida que el contexto de Spring Boot arranca.
 *
 * <p>Nota: se declara explícitamente la clase principal para evitar problemas de "package scanning"
 * cuando el paquete del test no coincide exactamente con el de la aplicación.</p>
 */
@SpringBootTest(classes = NbaBackendApplication.class)
class NbaBackendApplicationTests {

    @Test
    void contextLoads() {
    }
}

