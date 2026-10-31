package com.juniverse.crud.domain.exception;

/** Excepción base del dominio. */
public class DomainException extends RuntimeException {

    /**
     * Crea la excepción.
     * @param message descripción del error
     */
    public DomainException(String message) {
        super(message);
    }
}
