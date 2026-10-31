package com.juniverse.crud.domain.port.out;

import com.juniverse.crud.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ProductRepositoryPort
 */
public interface ProductRepositoryPort {

    /** Todos los productos. */
    Flux<Product> findAll();

    /**
     * Busca por id.
     * @param id identificador
     */
    Mono<Product> findById(Long id);

    /**
     * Productos de una categoría.
     * @param categoryId identificador de la categoría
     */
    Flux<Product> findByCategoryId(Long categoryId);

    /**
     * Busca por nombre (sin distinguir mayúsculas).
     * @param name texto a buscar
     */
    Flux<Product> findByNameContaining(String name);

    /**
     * Inserta o actualiza.
     * @param product producto a guardar
     */
    Mono<Product> save(Product product);

    /**
     * Elimina por id.
     * @param id identificador
     */
    Mono<Void> deleteById(Long id);

    /**
     * Indica si la categoría tiene productos.
     * @param categoryId identificador de la categoría
     */
    Mono<Boolean> existsByCategoryId(Long categoryId);
}
