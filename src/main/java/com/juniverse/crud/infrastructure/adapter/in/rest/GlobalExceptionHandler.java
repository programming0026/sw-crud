package com.juniverse.crud.infrastructure.adapter.in.rest;

import com.juniverse.crud.domain.exception.BusinessRuleException;
import com.juniverse.crud.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

/** Convierte las excepciones en respuestas JSON {@code {"error": "..."}}. */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 404: la entidad no existe. */
    @ExceptionHandler(EntityNotFoundException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleEntityNotFound(EntityNotFoundException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage())));
    }

    /** 400: se incumple una regla de negocio. */
    @ExceptionHandler(BusinessRuleException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleBusinessRule(BusinessRuleException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage())));
    }

    /** 400: cuerpo o parámetros inválidos (por ejemplo, JSON mal formado). */
    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleBadInput(ServerWebInputException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid request: " + ex.getReason())));
    }

    /** 500: error inesperado (se registra en el log y no se expone el detalle). */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, String>>> handleGenericException(Exception ex) {
        log.error("Unexpected error", ex); // antes el error se perdía sin dejar rastro
        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error occurred")));
    }
}
