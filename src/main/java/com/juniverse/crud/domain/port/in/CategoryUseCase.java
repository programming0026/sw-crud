package com.juniverse.crud.domain.port.in;

import com.juniverse.crud.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Puerto de entrada: operaciones sobre categorías. */
public interface CategoryUseCase {

    /** Lista todas las categorías. */
    Flux<Category> findAll();

    /**
     * Busca una categoría por id (404 si no existe).
     * @param id identificador
     */
    Mono<Category> findById(Long id);

    /**
     * Busca categorías por nombre.
     * @param name texto a buscar
     */
    Flux<Category> findByName(String name);

    /**
     * Crea una categoría.
     * @param category datos
     */
    Mono<Category> create(Category category);

    /**
     * Actualiza una categoría.
     * @param id identificador
     * @param category nuevos datos
     */
    Mono<Category> update(Long id, Category category);

    /**
     * Elimina una categoría sin productos.
     * @param id identificador
     */
    Mono<Void> delete(Long id);
}
