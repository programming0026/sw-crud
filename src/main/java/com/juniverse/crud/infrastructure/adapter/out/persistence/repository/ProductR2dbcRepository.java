package com.juniverse.crud.infrastructure.adapter.out.persistence.repository;

import com.juniverse.crud.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Repositorio reactivo de productos. */
public interface ProductR2dbcRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    /** Productos de una categoría. */
    Flux<ProductEntity> findByCategoryId(Long categoryId);

    /** Busca por nombre sin distinguir mayúsculas. */
    Flux<ProductEntity> findByNameContainingIgnoreCase(String name);

    Mono<Boolean> existsByCategoryId(Long categoryId);
}
