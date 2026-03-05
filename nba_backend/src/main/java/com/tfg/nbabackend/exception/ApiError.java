package com.tfg.nbabackend.exception;

/**
 * DTO para respuestas de error/mensaje en formato JSON.
 * Evita que el cliente (Android) falle con "Expected BEGIN_OBJECT but was STRING"
 * cuando el backend devuelve texto plano.
 */
public record ApiError(String error) {
}
