package com.juniverse.crud.domain.port.in;

import com.juniverse.crud.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Puerto de entrada: operaciones sobre productos. */
public interface ProductUseCase {

    /** Lista todos los productos. */
    Flux<Product> findAll();

    /**
     * Busca un producto por id (404 si no existe).
     * @param id identificador
     */
    Mono<Product> findById(Long id);

    /**
     * Productos de una categoría.
     * @param categoryId identificador de la categoría
     */
    Flux<Product> findByCategoryId(Long categoryId);

    /**
     * Busca productos por nombre.
     * @param name texto a buscar
     */
    Flux<Product> findByName(String name);

    /**
     * Crea un producto.
     * @param product datos
     */
    Mono<Product> create(Product product);

    /**
     * Actualiza un producto.
     * @param id identificador
     * @param product nuevos datos
     */
    Mono<Product> update(Long id, Product product);

    /**
     * Elimina un producto.
     * @param id identificador
     */
    Mono<Void> delete(Long id);
}
