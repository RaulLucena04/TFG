package com.tfg.nbabackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para devolver siempre respuestas JSON en errores.
 * 
 * <p>Intercepta todas las excepciones no manejadas y las convierte en respuestas
 * JSON estructuradas. Esto evita respuestas con texto plano que provocan errores
 * de deserialización en los clientes (especialmente Android con Retrofit).
 * 
 * <p>Tipos de excepciones manejadas:
 * <ul>
 *   <li>RuntimeException: Devuelve 400 Bad Request</li>
 *   <li>Exception: Devuelve 500 Internal Server Error</li>
 * </ul>
 * 
 * @author TFG
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo RuntimeException.
     * 
     * <p>Generalmente son errores de validación o lógica de negocio.
     * 
     * @param ex la excepción capturada
     * @return respuesta HTTP 400 con el mensaje de error en formato JSON
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage()));
    }

    /**
     * Maneja excepciones genéricas no capturadas.
     * 
     * <p>Errores inesperados del servidor.
     * 
     * @param ex la excepción capturada
     * @return respuesta HTTP 500 con el mensaje de error en formato JSON
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(ex.getMessage() != null ? ex.getMessage() : "Error interno del servidor"));
    }
}
