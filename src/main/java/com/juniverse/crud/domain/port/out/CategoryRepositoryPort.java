package com.juniverse.crud.domain.port.out;

import com.juniverse.crud.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CategoryRepositoryPort
 */
public interface CategoryRepositoryPort {

    /** Todas las categorías. */
    Flux<Category> findAll();

    /**
     * Busca por id.
     * @param id identificador
     */
    Mono<Category> findById(Long id);

    /**
     * Busca por nombre (sin distinguir mayúsculas).
     * @param name texto a buscar
     */
    Flux<Category> findByNameContaining(String name);

    /**
     * Inserta o actualiza.
     * @param category categoría a guardar
     */
    Mono<Category> save(Category category);

    /**
     * Elimina por id.
     * @param id identificador
     */
    Mono<Void> deleteById(Long id);
}
