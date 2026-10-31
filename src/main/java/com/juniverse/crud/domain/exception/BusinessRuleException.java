package com.juniverse.crud.domain.exception;

/** Se incumple una regla de negocio (se responde 400). */
public class BusinessRuleException extends DomainException {

    /**
     * Crea la excepción.
     * @param message descripción de la regla incumplida
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
