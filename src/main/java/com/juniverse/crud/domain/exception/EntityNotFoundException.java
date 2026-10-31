package com.juniverse.crud.domain.exception;

/** No existe la entidad pedida (se responde 404). */
public class EntityNotFoundException extends DomainException {

    /**
     * Crea la excepción.
     * @param entity nombre de la entidad
     * @param id identificador que no existe
     */
    public EntityNotFoundException(String entity, Long id) {
        super(entity + " not found with id: " + id);
    }
}
